package com.example.customviewapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BaseView extends View {
    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //底色
        canvas.drawColor(Color.YELLOW);

        //染料
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);//染料的颜色
        paint.setStyle(Paint.Style.FILL);// Style 修改为画线模式
        paint.setStrokeWidth(4);//边框宽度
        paint.setAntiAlias(true);//抗锯齿

        //基本的canvas
       /* canvas.drawCircle(400, 400, 200, paint);

        RectF rectF = new RectF(300, 800, 700, 1200);
        canvas.drawRect(rectF, paint);

        canvas.drawPoint(100, 100, paint);

        rectF.set(300, 1200, 700, 1400);
        canvas.drawOval(rectF, paint);

        canvas.drawLine(400, 10, 800, 10, paint);

        rectF.set(300, 1500, 700, 1700);
        canvas.drawRoundRect(rectF, 20, 20, paint);

        canvas.drawArc(rectF, 0, 120, true, paint);

        Path path = new Path(); // 初始化 Path 对象
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        path.close();
        canvas.drawPath(path, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        canvas.drawBitmap(bitmap, 100, 100, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);
        canvas.drawText("hello word", 100, 100, paint);*/

        //着色器
        /*paint.setStyle(Paint.Style.FILL);// Style 修改为画线模式
        Shader shader = new LinearGradient(10, 10, 210, 210, Color.YELLOW, Color.BLUE, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawLine(10, 10, 210, 210, paint);

        shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(300, 300, 200, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        shader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawCircle(130, 130, 130, paint);

        // 第一个 Shader：头像的 Bitmap    //dst
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 第二个 Shader   //src
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);
        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // ComposeShader：结合两个 Shader
        shader = new ComposeShader(shader1, shader2, PorterDuff.Mode.MULTIPLY);
        paint.setShader(shader);
        canvas.drawCircle(200, 200, 200, paint);*/

        //Xfermode
        /*int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        paint.setStyle(Paint.Style.FILL);
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        RectF f = new RectF(100, 100, 500, 500);
        canvas.drawRect(f, paint);

        paint.setColor(Color.RED);
        paint.setXfermode(xfermode); // 设置 Xfermode
        canvas.drawCircle(600, 600, 300, paint); // 画圆
        paint.setXfermode(null); // 用完及时清除 Xfermode

        canvas.restoreToCount(saved);*/

        //文字的绘制
       /* String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        paint.setTextSize(40);//文字大小
        paint.setTypeface(Typeface.MONOSPACE);//设置字体
        paint.setFakeBoldText(false);//是否使用伪粗体
        paint.setStrikeThruText(false);//删除线
        paint.setUnderlineText(false);//下划线
        paint.setTextAlign(Paint.Align.LEFT);//文字对齐方式
        canvas.drawText(text, 200, 100, paint);

        //多行文字的绘制
        TextPaint textPaint = new TextPaint(paint);
        StaticLayout staticLayout1 = new StaticLayout(text, textPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        staticLayout1.draw(canvas);*/

        //测量文字尺寸
        paint.getFontSpacing();//获取推荐的行距

        String str = "Hello world girl";
        int offsetX = 40;
        int offsetY = 100;
        canvas.drawText(str, offsetX, offsetY, paint);

//        Rect e = new Rect();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.getTextBounds(str, 0, 11, e);//获取文字的显示范围 数据封装在Rect中
//        e.left += offsetX;
//        e.top += offsetY;
//        e.right += offsetX;
//        e.bottom += offsetY;
//        canvas.drawRect(e, paint);
//
//        float width = paint.measureText(str);//获取文字的显示宽度 > 大于getTextBounds()获取到的宽度
//        canvas.drawLine(offsetX, offsetY, offsetX + width, offsetY, paint);
//
//        paint.breakText();//用于多行文字的折行计算

//        canvas 范围裁切(限制绘制的范围)
//        canvas.clipRect(0, 0, 200, 200); //绘制的内容在矩形内

//        Path path = new Path();
//        path.addCircle(140, 140, 100, Path.Direction.CCW);
//        canvas.clipPath(path);

        //  Canvas二维变化
//        canvas.translate(100, 200);// 平移画布
//        canvas.rotate(45);//旋转 锚点在View的坐标系原点
//        canvas.rotate(90, 140, 140);//旋转 指定轴心点坐标
//        canvas.scale(1.5f, 0.5f);//放缩
//        canvas.skew(0.0f, 1f);//错切？？？

        //  Matrix二维变化
//        Matrix matrix = new Matrix();
//        matrix.reset();
//        matrix.postTranslate(100, 100);
//        matrix.postRotate(45);
//        matrix.setPolyToPoly();//点对点映射的方式设置变换
//
//        canvas.save();
//        canvas.concat(matrix);//合并传入Matrix
//        canvas.setMatrix(matrix);//传入的Matrix会替换原来的Matrix
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        canvas.restore();

        // Camera可实现三维变化

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //获得super.onMeasure()测量到的尺寸
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        //重新设置View大小
//        setMeasuredDimension(width / 4, height / 4);

        int width = 3000;
        int height = 5000;
        //修正尺寸
        height = resolveSize(height, heightMeasureSpec);
        width = resolveSize(width, widthMeasureSpec);
        //重新设置View大小
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

}