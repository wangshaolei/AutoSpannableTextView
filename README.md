# AutoSpannableTextView
Support some of the key words can be clicked with the underline TextView<br>

[![](https://jitpack.io/v/wangshaolei/AutoSpannableTextView.svg)](https://jitpack.io/#wangshaolei/AutoSpannableTextView)


Step1：

```xml
    <declare-styleable name="AutoLinkStyleTextView">
        <attr name="AutoLinkStyleTextView_text_value" format="string|reference"/>//key word with color and underline, and split with ','(en)
        <attr name="AutoLinkStyleTextView_default_color" format="color|reference"/>//word and underline's color
        <attr name="AutoLinkStyleTextView_has_under_line" format="boolean"/>//underline with true and false
        <attr name="AutoLinkStyleTextView_start_image" format="reference"/>//start with image 's TextView
        <attr name="AutoLinkStyleTextView_type">
            <enum name="start_image" value="0"/>
            <enum name="content_text" value="1"/>//default
        </attr>
    </declare-styleable>
```
<br>

Step2：

```xml
    //style1
    <xx.AutoLinkStyleTextView
        android:id="@+id/tv_clause"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="我已核对付款金额，仔细阅读并同意“购买须知”及约克论坛团购“用户条款”"
        android:textSize="16sp"
        app:AutoLinkStyleTextView_text_value="“购买须知”,“用户条款”"
        />
    //style2
    <xx.AutoLinkStyleTextView
        android:id="@+id/tv_start_image"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="活动介绍，开始以图片开头"
        android:textSize="16sp"
        app:AutoLinkStyleTextView_start_image="@mipmap/ic_qupai_subject_information"
        app:AutoLinkStyleTextView_type="start_image"
        />

```
Step3：

```java

    //style1
    autoLinkStyleTextView.setOnClickCallBack(new AutoLinkStyleTextView.ClickCallBack() {
        @Override
        public void onClick(int position) {
            if (position == 0) {
                Toast.makeText(MainActivity.this, "购买须知", Toast.LENGTH_SHORT).show();
            } else if (position == 1) {
                Toast.makeText(MainActivity.this, "用户条款", Toast.LENGTH_SHORT).show();
            }
        }
     });
     
     //style2
     setStartImageText(tvStartImage.getText());
```

![](https://github.com/wangshaolei/UnderLineLinkTextView/blob/master/img/1.png)
![](https://github.com/wangshaolei/UnderLineLinkTextView/blob/master/img/2.png)
![](https://github.com/wangshaolei/UnderLineLinkTextView/blob/master/img/3.png)
