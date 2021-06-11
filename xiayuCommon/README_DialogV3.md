Kongzue Dialog V3使用方法：https://github.com/kongzue/DialogV3/blob/master/README.md

全局配置

DialogSettings.isUseBlur = (boolean);                   //是否开启模糊效果，默认关闭
DialogSettings.modalDialog = (boolean);                 //是否开启模态窗口模式，一次显示多个对话框将以队列形式一个一个显示，默认关闭
DialogSettings.style = (DialogSettings.STYLE);          //全局主题风格，提供三种可选风格，STYLE_MATERIAL, STYLE_KONGZUE, STYLE_IOS
DialogSettings.theme = (DialogSettings.THEME);          //全局对话框明暗风格，提供两种可选主题，LIGHT, DARK
DialogSettings.tipTheme = (DialogSettings.THEME);       //全局提示框明暗风格，提供两种可选主题，LIGHT, DARK
DialogSettings.titleTextInfo = (TextInfo);              //全局对话框标题文字样式
DialogSettings.menuTitleInfo = (TextInfo);              //全局菜单标题文字样式
DialogSettings.menuTextInfo = (TextInfo);               //全局菜单列表文字样式
DialogSettings.contentTextInfo = (TextInfo);            //全局正文文字样式
DialogSettings.buttonTextInfo = (TextInfo);             //全局默认按钮文字样式
DialogSettings.buttonPositiveTextInfo = (TextInfo);     //全局焦点按钮文字样式（一般指确定按钮）
DialogSettings.inputInfo = (InputInfo);                 //全局输入框文本样式
DialogSettings.backgroundColor = (ColorInt);            //全局对话框背景颜色，值0时不生效
DialogSettings.cancelable = (boolean);                  //全局对话框默认是否可以点击外围遮罩区域或返回键关闭，此开关不影响提示框（TipDialog）以及等待框（TipDialog）
DialogSettings.cancelableTipDialog = (boolean);         //全局提示框及等待框（WaitDialog、TipDialog）默认是否可以关闭
DialogSettings.DEBUGMODE = (boolean);                   //是否允许打印日志
DialogSettings.blurAlpha = (int);                       //开启模糊后的透明度（0~255）
DialogSettings.systemDialogStyle = (styleResId);        //自定义系统对话框style，注意设置此功能会导致原对话框风格和动画失效
DialogSettings.dialogLifeCycleListener = (DialogLifeCycleListener);  //全局Dialog生命周期监听器
DialogSettings.defaultCancelButtonText = (String);      //设置 BottomMenu 和 ShareDialog 默认“取消”按钮的文字
DialogSettings.tipBackgroundResId = (drawableResId);    //设置 TipDialog 和 WaitDialog 的背景资源
DialogSettings.tipTextInfo = (InputInfo);               //设置 TipDialog 和 WaitDialog 文字样式
DialogSettings.autoShowInputKeyboard = (boolean);       //设置 InputDialog 是否自动弹出输入法
DialogSettings.okButtonDrawable = (drawable);           //设置确定按钮背景资源
DialogSettings.cancelButtonDrawable = (drawable);       //设置取消按钮背景资源
DialogSettings.otherButtonDrawable = (drawable);        //设置其他按钮背景资源

//检查 Renderscript 兼容性，若设备不支持，DialogSettings.isUseBlur 会自动关闭；
boolean renderscriptSupport = DialogSettings.checkRenderscriptSupport(context)

DialogSettings.init(context);                           //初始化清空 BaseDialog 队列



基本消息对话框：MessageDialog.show(MainActivity.this, "提示", "这是一条消息", "确定");
                也可以通过 build(...) 方法创建，并定制更多效果：
输入对话框：InputDialog.show(MainActivity.this, "输入对话框", "输入一些内容", "确定");

等待和提示对话框：WaitDialog.show(MainActivity.this, "请稍候...");

底部菜单：BottomMenu.show(MainActivity.this, new String[]{"菜单1", "菜单2", "菜单3"}, new OnMenuItemClickListener() {
         @Override
         public void onClick(String text, int index) {
             //返回参数 text 即菜单名称，index 即菜单索引
         }
     });

通知：Notification.show(MainActivity.this, "提示", "提示信息");

分享对话框：
      List<ShareDialog.Item> itemList = new ArrayList<>();
      itemList.add(new ShareDialog.Item(MainActivity.this ,R.mipmap.img_email_ios,"邮件"));
      itemList.add(new ShareDialog.Item(MainActivity.this ,R.mipmap.img_qq_ios,"QQ"));
      itemList.add(new ShareDialog.Item(MainActivity.this ,R.mipmap.img_wechat_ios,"微信"));
      itemList.add(new ShareDialog.Item(MainActivity.this ,R.mipmap.img_weibo_ios,"微博"));
      itemList.add(new ShareDialog.Item(MainActivity.this ,R.mipmap.img_memorandum_ios,"添加到“备忘录”"));
      itemList.add(new ShareDialog.Item(MainActivity.this ,R.mipmap.img_remind_ios,"提醒事项"));
      ShareDialog.show(MainActivity.this, itemList, new ShareDialog.OnItemClickListener() {
          @Override
          public boolean onClick(ShareDialog shareDialog, int index, ShareDialog.Item item) {
              toast("点击了：" + item.getText());
              return false;
          }
      });

全屏对话框：FullScreenDialog
              .show(MainActivity.this, R.layout.layout_full_login, new FullScreenDialog.OnBindView() {
                  @Override
                  public void onBind(FullScreenDialog dialog, View rootView) {
                      boxUserName = rootView.findViewById(R.id.box_userName);
                      editUserName = rootView.findViewById(R.id.edit_userName);
                      boxPassword = rootView.findViewById(R.id.box_password);
                      editPassword = rootView.findViewById(R.id.edit_password);
                  }
              })
              .setOkButton("下一步", nextStepListener)
              .setCancelButton("取消")
              .setTitle("登录");


定制化
自定义布局：
        //对于未实例化的布局：
        MessageDialog.show(MainActivity.this, "提示", "这个窗口附带自定义布局", "知道了")
                .setCustomView(R.layout.layout_custom, new MessageDialog.OnBindView() {
                    @Override
                    public void onBind(MessageDialog dialog, View v) {
                        //绑定布局事件，可使用v.findViewById(...)来获取子组件
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toast("点击了自定义布局");
                            }
                        });
                    }
                });

                //对于已实例化的布局：
                View customView;
                MessageDialog.show(MainActivity.this, "提示", "这个窗口附带自定义布局", "知道了")
                        .setCustomView(customView);
        目前支持自定义子布局的有：消息对话框组件（MessageDialog）、底部菜单组件（BottomMenu）、输入框组件（InputDialog）、分享对话框（ShareDialog）和通知组件（Notification）


自定义对话框：
        //对于未实例化的布局：
        CustomDialog.show(MainActivity.this, R.layout.layout_custom_dialog, new CustomDialog.OnBindView() {
            @Override
            public void onBind(final CustomDialog dialog, View v) {
                ImageView btnOk = v.findViewById(R.id.btn_ok);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.doDismiss();
                    }
                });
            }
        });

        //对于已实例化的布局：
        View customView;
        CustomDialog.show(MainActivity.this, customView, new CustomDialog.OnBindView() {
            @Override
            public void onBind(final CustomDialog dialog, View v) {
                ImageView btnOk = v.findViewById(R.id.btn_ok);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.doDismiss();
                    }
                });
            }
        });
        其他方法

        //全屏幕宽高
        customDialog.setFullScreen(true);

        //设置 CustomDialog 处于屏幕的位置
        customDialog.setAlign(CustomDialog.ALIGN.BOTTOM)        //从屏幕底端出现
        customDialog.setAlign(CustomDialog.ALIGN.TOP)           //从屏幕顶端出现
        customDialog.setAlign(CustomDialog.ALIGN.DEFAULT)       //从屏幕中部出现


自定义对话框按钮背景（颜色）资源：
        除 Material 风格外，iOS 和 Kongzue 风格支持自定义 drawable 的方式修改按钮背景（颜色）资源：

        您可以通过以下方式全局指定自定义按钮背景资源：

        //修改确定按钮背景资源：
        DialogSettings.okButtonDrawable = getDrawable(R.drawable.btn_ok);
        //其他按钮：
        DialogSettings.cancelButtonDrawable = getDrawable(R.drawable.btn_cancel);
        DialogSettings.otherButtonDrawable = getDrawable(R.drawable.btn_other);
        也可以单独指定对话框的按钮背景资源：

        //使用资源 id：
        messageDialog.setOkButtonDrawable(resId);
        //或直接使用 drawable：
        messageDialog.setOkButtonDrawable(drawable);
        drawable资源可按照如下方式设计： btn_ok.xml

        <?xml version="1.0" encoding="utf-8"?>
        <selector xmlns:android="http://schemas.android.com/apk/res/android">
            <item android:drawable="@color/dialogButtonBlueLightPress" android:state_pressed="true" />
            <item android:drawable="@color/dialogButtonBlueLight" android:state_focused="false" android:state_pressed="false" />
            <item android:drawable="@color/dialogButtonBlueLight" android:state_focused="true" />
            <item android:drawable="@color/dialogButtonBlueLight" android:state_focused="false" />
        </selector>
        其中，dialogButtonBlueLight为默认颜色，dialogButtonBlueLightPress为按下时颜色，请在您的colors.xml资源文件中添加其颜色。

其他设置
通用功能
        屏幕顶端、底部弹出

        //从顶部弹出
        Dialog.build(me)
            .setAlign(BaseDialog.ALIGN.TOP);

        //从底部弹出
        Dialog.build(me)
            .setAlign(BaseDialog.ALIGN.BOTTOM);
        显示时执行

        dialog.setOnShowListener(onShowListener);
        关闭时执行

        dialog.setOnDismissListener(onDismissListener);
        设置是否可以点击外部区域或“返回”按键关闭对话框：

        dialog.setCancelable(boolean);
        设置“返回”按键监听

        dialog.setOnBackClickListener(new OnBackClickListener() {
            @Override
            public boolean onBackClick() {
                toast("按下返回！");
                return true;        //return 结果代表是否拦截此事件
            }
        });
        使用自定义的 Dialog style

        dialog.setCustomDialogStyleId(R.style.XXX);


文字样式
        因文字样式牵扯的属性较多，因此提供了封装类 TextInfo（com.kongzue.dialog.util.TextInfo）来进行。

        该类提供了以下属性进行设置：

        属性	    用途	            默认值
        fontSize	文字大小(单位：dp)	值为-1时不生效
        gravity	    对齐方式	        Gravity.Left，值为-1时不生效
        fontColor	文字颜色	        值为1时不生效
        bold	    是否粗体	        -
        以上属性可通过对应的 get、set方法设置或获取

        您可以直接进行 全局设置 也可以单独对某个组件的标题、内容、按钮等进行设置：

        MessageDialog.show(MainActivity.this, "提示", "这个窗口附带自定义布局", "知道了")
            .setTitleTextInfo(new TextInfo().setBold(true).setFontColor(Color.RED))     //设置标题文字样式
        ;
输入内容设置
        对于输入对话框 InputDialog，提供额外的 InputInfo（com.kongzue.dialog.util.InputInfo） 工具类控制输入内容的属性，其中各属性介绍如下：

        属性	        用途	    默认值
        MAX_LENGTH	可输入最大长度	值为-1时不生效
        inputType	输入类型	    类型详见 android.text.InputType
        textInfo	文字样式	    null时不生效

        您可以直接进行 全局设置 也可以单独对某个输入对话框进行设置：

        InputDialog.show(MainActivity.this, "提示", "请输入密码（123456）", "确定", "取消")
            .setInputInfo(new InputInfo()       //设置输入样式
                .setSelectAllText(true)                                     //默认选中全部文字
                .setMAX_LENGTH(6)                                           //最大允许6个字
                .setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)       //密码类型
                .setTextInfo(new TextInfo()     //设置文字样式
                                     .setFontColor(Color.RED)               //颜色指定为红色
                )
        ;
监听事件
        如果需要全局的控制所有对话框显示、隐藏触发事件，可以设置 全局设置 中的 dialogLifeCycleListener 监听器，其中会返回所有对话框的生命周期管理，以便做相应处理：

        DialogSettings.dialogLifeCycleListener = new DialogLifeCycleListener() {
            @Override
            public void onCreate(BaseDialog dialog) {

            }
            @Override
            public void onShow(BaseDialog dialog) {

            }
            @Override
            public void onDismiss(BaseDialog dialog) {

            }
        }
        要单独对某个对话框进行监听，可使用对应的 setOnShowListener(...) 及 setOnDismissListener(...) 进行处理，例如，在提示过后关闭本界面可以这样写：

        TipDialog.show(MainActivity.this, "成功！", TipDialog.TYPE.SUCCESS).setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
});
自定义背景
        目前 MessageDialog、InpurDialog、TipDialog、WaitDialog 支持使用以下方法自定义背景资源：

        dialog.setBackgroundResId(int resId);