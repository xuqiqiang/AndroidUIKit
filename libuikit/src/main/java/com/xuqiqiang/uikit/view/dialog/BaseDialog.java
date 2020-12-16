package com.xuqiqiang.uikit.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.xuqiqiang.uikit.R;
import com.xuqiqiang.uikit.utils.DisplayUtils;

import java.util.ArrayList;

/**
 * Created by xuqiqiang on 2016/05/17.
 */
public class BaseDialog extends Dialog {

    private static int mDefaultWidth = 260;
    private static int mDefaultDialogLayout = R.layout.dialog_base;
    private View innerView;

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    public BaseDialog(Context context) {
        super(context);
    }

    public static void setDefaultDialogLayout(@LayoutRes int layoutId) {
        mDefaultDialogLayout = layoutId;
    }

    public static void setDefaultWidth(int width) {
        mDefaultWidth = width;
    }

    public static BaseDialog show(Context context, String title, String message) {
        return show(context, title, message, null);
    }

    public static BaseDialog show(Context context, String title, String message, final OnDialogListener onPositive) {
        BaseDialog dialog = new BaseDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onPositive == null) dialog.cancel();
                        else if (onPositive.onClick(dialog)) dialog.cancel();
                    }
                }).create();
        dialog.show();
        return dialog;
    }

    public static BaseDialog show(Context context, String title, String message,
                                  final OnDialogListener onPositive, final OnDialogListener onNegative) {
        BaseDialog dialog = new BaseDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onPositive == null) dialog.cancel();
                        else if (onPositive.onClick(dialog)) dialog.cancel();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onNegative == null) dialog.cancel();
                        else if (onNegative.onClick(dialog)) dialog.cancel();
                    }
                }).create();
        dialog.show();
        return dialog;
    }

    public View getInnerView() {
        return innerView;
    }

    public void setInnerView(@NonNull View innerView) {
        this.innerView = innerView;
    }

    public interface OnDialogListener {
        boolean onClick(DialogInterface dialog);
    }

    public interface OnContentViewListener {
        void onCreateView(View view);
    }

    /**
     * Helper class for creating a base dialog
     */
    public static class Builder {
        private static final int SELECT_NONE = -99;
        private final Context context;
        private String title;
        private String message;
        private String[] items;
        private int[] itemsDrawable;
        private int oldSelect = SELECT_NONE;
        private String positiveButtonText;
        private String negativeButtonText;
        private String extraButtonText;
        private int positiveButtonTextColor;
        private int negativeButtonTextColor;
        private boolean cancelable = true;
        //        private boolean showClose = true;
        private int dialogLayout = mDefaultDialogLayout;
        private View contentView;
        private int contentLayout;
        private OnContentViewListener onContentViewListener;
        private View layout;
        private int width;

        private OnClickListener positiveButtonClickListener,
                negativeButtonClickListener,
                extraButtonClickListener,
                onKeyBackListener;
        private DialogInterface.OnClickListener itemsClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the Dialog message from String
         *
         * @param message The message to show
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message The message to show
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }


        /**
         * Set the Dialog items from resourceId
         *
         * @param items
         * @param itemsClickListener
         * @return
         */
        public Builder setItems(int items,
                                DialogInterface.OnClickListener itemsClickListener) {
            this.items = context.getResources().getStringArray(items);
            this.itemsClickListener = itemsClickListener;
            return this;
        }

        /**
         * Set the Dialog items from String
         *
         * @param items
         * @param itemsClickListener
         * @return
         */
        public Builder setItems(String[] items,
                                DialogInterface.OnClickListener itemsClickListener) {
            this.items = items;
            this.itemsClickListener = itemsClickListener;
            return this;
        }

        /**
         * Set the Dialog items from resourceId
         *
         * @param items
         * @param itemsClickListener
         * @return
         */
        public Builder setItems(int[] items,
                                DialogInterface.OnClickListener itemsClickListener) {
            this.items = new String[items.length];
            for (int i = 0; i < items.length; i++) {
                this.items[i] = context.getString(items[i]);
            }
            this.itemsClickListener = itemsClickListener;
            return this;
        }

        /**
         * Set the Dialog items from String
         *
         * @param items
         * @param itemsClickListener
         * @return
         */
        public Builder setItems(ArrayList<String> items,
                                DialogInterface.OnClickListener itemsClickListener) {
            if (items != null)
                this.items = items.toArray(new String[0]);
            this.itemsClickListener = itemsClickListener;
            return this;
        }

        /**
         * Set the Dialog items from ListItem
         *
         * @param items
         * @return
         */
        public Builder setItems(final ListItem[] items, final Object... args) {
            if (items == null)
                throw new IllegalStateException("items is null");
            this.items = new String[items.length];
            this.itemsDrawable = new int[items.length];
            for (int i = 0; i < items.length; i++) {
                this.items[i] = items[i].name;
                this.itemsDrawable[i] = items[i].drawable;
            }
            this.itemsClickListener = new DialogInterface.OnClickListener() {
                public void onClick(
                        DialogInterface dialog,
                        final int which) {
                    items[which].onClick(args);
                    dialog.cancel();
                }
            };
            return this;
        }

        /**
         * Set the Dialog items from String
         *
         * @param items
         * @param itemsClickListener
         * @return
         */
        public Builder setItems(String[] items, int oldSelect,
                                DialogInterface.OnClickListener itemsClickListener) {
            this.items = items;
            this.oldSelect = oldSelect;
            this.itemsClickListener = itemsClickListener;
            return this;
        }

        /**
         * Set the Dialog items from String
         *
         * @param items
         * @param itemsClickListener
         * @return
         */
        public Builder setItems(ArrayList<String> items, int oldSelect,
                                DialogInterface.OnClickListener itemsClickListener) {
            if (items != null)
                this.items = items.toArray(new String[0]);
            this.oldSelect = oldSelect;
            this.itemsClickListener = itemsClickListener;
            return this;
        }

        /**
         * Set the Dialog items from String
         *
         * @param items
         * @param itemsClickListener
         * @return
         */
        public Builder setItems(int items, int oldSelect,
                                DialogInterface.OnClickListener itemsClickListener) {
            this.items = context.getResources().getStringArray(items);
            this.oldSelect = oldSelect;
            this.itemsClickListener = itemsClickListener;
            return this;
        }

        /**
         * Set the Dialog itemsDrawable
         *
         * @param itemsDrawable
         * @return
         */
        public Builder setItemsDrawable(int[] itemsDrawable) {
            this.itemsDrawable = itemsDrawable;
            return this;
        }

        public Builder setDialogView(int layoutId) {
            this.dialogLayout = layoutId;
            return this;
        }

        /**
         * Set a custom content view for the Dialog. If a message is set, the
         * contentView is not added to the Dialog...
         *
         * @param view
         * @return
         */
        public Builder setContentView(View view) {
            this.contentView = view;
            return this;
        }

        /**
         * Set a custom content view for the Dialog. If a message is set, the
         * contentView is not added to the Dialog...
         *
         * @param layoutId
         * @return
         */
        public Builder setContentView(int layoutId) {
            this.contentLayout = layoutId;
            return this;
        }

        public Builder setContentView(int layoutId, OnContentViewListener listener) {
            this.contentLayout = layoutId;
            this.onContentViewListener = listener;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            if (listener != null)
                this.positiveButtonClickListener = listener;
            else {
                this.positiveButtonClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
            }
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            if (listener != null)
                this.positiveButtonClickListener = listener;
            else {
                this.positiveButtonClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
            }
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText,
                                         int positiveButtonTextColor,
                                         OnClickListener listener) {
            this.positiveButtonTextColor = positiveButtonTextColor;
            return setPositiveButton(positiveButtonText, listener);
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         int positiveButtonTextColor,
                                         OnClickListener listener) {
            this.positiveButtonTextColor = positiveButtonTextColor;
            return setPositiveButton(positiveButtonText, listener);
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            if (listener != null)
                this.negativeButtonClickListener = listener;
            else {
                this.negativeButtonClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
            }
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            if (listener != null)
                this.negativeButtonClickListener = listener;
            else {
                this.negativeButtonClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
            }
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         int negativeButtonTextColor,
                                         OnClickListener listener) {
            this.negativeButtonTextColor = negativeButtonTextColor;
            return setNegativeButton(negativeButtonText, listener);
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         int negativeButtonTextColor,
                                         OnClickListener listener) {
            this.negativeButtonTextColor = negativeButtonTextColor;
            return setNegativeButton(negativeButtonText, listener);
        }

        public Builder setExtraButton(int extraButtonText,
                                      DialogInterface.OnClickListener listener) {
            this.extraButtonText = (String) context.getText(extraButtonText);

            if (listener != null)
                this.extraButtonClickListener = listener;
            else {
                this.extraButtonClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
            }
            return this;
        }

        public Builder setExtraButton(String extraButtonText,
                                      DialogInterface.OnClickListener listener) {
            this.extraButtonText = extraButtonText;
            if (listener != null)
                this.extraButtonClickListener = listener;
            else {
                this.extraButtonClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };
            }
            return this;
        }

        public Builder setOnKeyBackListener(
                OnClickListener listener) {
            this.onKeyBackListener = listener;
            return this;
        }

        public Builder setWidth(
                int width) {
            this.width = width;
            return this;
        }

        /**
         * Set the Dialog cancelable
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        /**
         * Get the Dialog View
         *
         * @return
         */
        public View getView() {
            return layout;
        }

        /**
         * Create the custom dialog
         */
        public BaseDialog create() {
            LayoutInflater inflater = LayoutInflater.from(context);
            // instantiate the dialog with the custom Theme
            final BaseDialog dialog = new BaseDialog(context,
                    R.style.CustomDialog);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(false);

            layout = inflater.inflate(dialogLayout, null);
            TextView tvTitle = layout.findViewById(R.id.tv_title);
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setVisibility(View.GONE);
            }

            if (items != null) {
                contentView = inflater.inflate(R.layout.dialog_custom_list_view, null);
                final ListView lv = contentView.findViewById(R.id.select_list);

                if (oldSelect == SELECT_NONE) {
                    lv.setAdapter(new ListAdapter(false));
                    if (itemsClickListener != null)
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1,
                                                    int position, long arg3) {
                                itemsClickListener.onClick(dialog, position);
                            }
                        });
                } else {
                    lv.setAdapter(new ListAdapter(true));
                    if (itemsClickListener != null)
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1,
                                                    int position, long arg3) {
                                lv.setItemChecked(position, true);
                                itemsClickListener.onClick(dialog, position);
                            }
                        });
                    lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    lv.setItemChecked(oldSelect, true);
                    lv.setSelection(oldSelect);
                }
            }

            if (contentView != null || contentLayout != 0) {
                // if no message set
                // add the contentView to the dialog body
                ViewGroup flContainer = layout.findViewById(R.id.fl_container);
                flContainer.removeAllViews();
                if (contentView != null) {
                    flContainer.addView(contentView, new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT));
                } else {
                    ViewGroup viewGroup = (ViewGroup) inflater.inflate(contentLayout, flContainer);
                    contentView = viewGroup.getChildAt(0);//inflater.inflate(contentLayout, flContainer);
//                    contentView = inflater.inflate(contentLayout, flContainer);
                    if (onContentViewListener != null)
                        onContentViewListener.onCreateView(contentView);
                }
                dialog.setInnerView(contentView);
            } else {
                TextView tvMessage = layout.findViewById(R.id.tv_message);
//                tvMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
                tvMessage.setText(message);
            }

            // set the confirm button
            View btOk = layout.findViewById(R.id.bt_ok);
            if (positiveButtonText != null) {
                btOk.setVisibility(View.VISIBLE);
                TextView tvOk = layout.findViewById(R.id.tv_ok);
                if (tvOk == null && btOk instanceof TextView) tvOk = (TextView) btOk;
                if (tvOk != null) {
                    tvOk.setText(positiveButtonText);
                    if (positiveButtonTextColor != 0)
                        tvOk.setTextColor(positiveButtonTextColor);
                }
                if (positiveButtonClickListener != null) {
                    btOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                btOk.setVisibility(View.GONE);
                View divider = layout.findViewById(R.id.divider);
                if (divider != null) divider.setVisibility(View.GONE);
            }
            // set the cancel button
            View btCancel = layout.findViewById(R.id.bt_cancel);
            if (negativeButtonText != null) {
                btCancel.setVisibility(View.VISIBLE);
                TextView tvCancel = layout.findViewById(R.id.tv_cancel);
                if (tvCancel == null && btCancel instanceof TextView)
                    tvCancel = (TextView) btCancel;
                if (tvCancel != null) {
                    tvCancel.setText(negativeButtonText);
                    if (negativeButtonTextColor != 0)
                        tvCancel.setTextColor(negativeButtonTextColor);
                }
                if (negativeButtonClickListener != null) {
                    btCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                btCancel.setVisibility(View.GONE);
                View divider = layout.findViewById(R.id.divider);
                if (divider != null) divider.setVisibility(View.GONE);
            }
            // set the extra button
            View btExtra = layout.findViewById(R.id.bt_extra);
            if (extraButtonText != null) {
                btExtra.setVisibility(View.VISIBLE);
                View divider = layout.findViewById(R.id.divider_extra);
                if (divider != null) divider.setVisibility(View.VISIBLE);
                TextView tvExtra = layout.findViewById(R.id.tv_extra);
                if (tvExtra == null && btExtra instanceof TextView) tvExtra = (TextView) btExtra;
                if (tvExtra != null) {
                    tvExtra.setText(extraButtonText);
//                    if (negativeButtonTextColor != 0)
//                        tvCancel.setTextColor(negativeButtonTextColor);
                }
                if (extraButtonClickListener != null) {
                    btExtra.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            extraButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEUTRAL);
                        }
                    });
                }
            }
            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (onKeyBackListener != null) {
                            onKeyBackListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                            return true;
                        }
                    }
                    return false;
                }
            });

            ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(
                    width == 0 ? (int) DisplayUtils.dip2px(context, mDefaultWidth) : width,
                    LayoutParams.WRAP_CONTENT);
            dialog.setContentView(layout, p);
            return dialog;
        }

        private class ListAdapter extends BaseAdapter {
            private final LayoutInflater mLayoutInflater;
            private final boolean isSelect;

            ListAdapter(boolean isSelect) {
                mLayoutInflater = LayoutInflater.from(context);
                this.isSelect = isSelect;
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public Object getItem(int arg0) {
                return items[arg0];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView,
                                ViewGroup parent) {

                if (convertView == null) {
                    if (!isSelect)
                        convertView = mLayoutInflater
                                .inflate(R.layout.custom_dialog_list_item,
                                        parent, false);
                    else
                        convertView = mLayoutInflater.inflate(
                                R.layout.custom_dialog_list_item_single_choice,
                                parent, false);
                }
                TextView text = convertView.findViewById(R.id.text);
                text.setText(items[position]);
                ViewGroup.LayoutParams lp = text.getLayoutParams();
                if (lp != null) {
                    lp.width = layout.getWidth() - (int) DisplayUtils.dip2px(context, 16);
                    text.setLayoutParams(lp);
                }

                if (itemsDrawable != null && itemsDrawable[position] != 0) {
//                    Drawable img;
//                    Resources res = context.getResources();
//                    img = res.getDrawable(itemsDrawable[position]);
//                    img.setBounds(0, 0, img.getMinimumWidth(),
//                            img.getMinimumHeight());
                    text.setCompoundDrawables(DisplayUtils.getDrawable(context, itemsDrawable[position]),
                            null, null, null);
                    text.setCompoundDrawablePadding((int) DisplayUtils.dip2px(
                            context, 10));
                }
                return convertView;

            }

        }
    }

    public static abstract class ListItem {
        public String name;
        public int drawable;

        public ListItem(String name,
                        int drawable) {
            this.name = name;
            this.drawable = drawable;
        }

        public abstract void onClick(Object... args);
    }
}