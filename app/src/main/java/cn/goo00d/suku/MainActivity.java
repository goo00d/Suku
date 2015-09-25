package cn.goo00d.suku;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Button btn[]=new Button[81];
    Button curBtn = null;
    int totolresnum=0;
    int cursel=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    Vector<Integer> outs[][] = new Vector[9][9];
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Button up1 = (Button)findViewById(R.id.before1);
        up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totolresnum>0)
                {
                    cursel--;
                    if (cursel<0) cursel+=totolresnum;
                    cursel%=totolresnum;
                    for (int i1 = 0; i1 < 81; i1++) {
                        int i = i1 / 9;
                        int j = i1 % 9;
                        btn[i1].setText(String.format("%d", outs[i][j].get(cursel)));
                    }
                    TextView curshow = (TextView)findViewById(R.id.cursel);
                    curshow.setText(String.format("%d",cursel+1));
                }

            }
        });

        Button down1 = (Button)findViewById(R.id.next1);
        down1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totolresnum>0)
                {
                    cursel++;
                    cursel%=totolresnum;
                    for (int i1 = 0; i1 < 81; i1++) {
                        int i = i1 / 9;
                        int j = i1 % 9;
                        btn[i1].setText(String.format("%d", outs[i][j].get(cursel)));
                    }
                    TextView curshow = (TextView)findViewById(R.id.cursel);
                    curshow.setText(String.format("%d",cursel+1));
                }
            }
        });




    for (int i=0;i<9;i++)
        for (int j=0;j<9;j++)
            outs[i][j] = new Vector();
        Button restall = (Button)findViewById(R.id.resetall);
        restall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i1=0;i1<81;i1++)
                {
                    btn[i1].setText("");
                    btn[i1].setBackgroundColor(Color.rgb(100, 100, 100));
                }
            }
        });
        Button delbt = (Button)findViewById(R.id.delselect);
        delbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i1=0;i1<81;i1++)
                {
                    int color1 = Color.rgb(100,100,100);
                    if (((ColorDrawable)btn[i1].getBackground()).getColor()==color1)
                    {
                        btn[i1].setText("");
                    }
                }
            }
        });
        final Button startcalc = (Button)findViewById(R.id.solvenow);
        startcalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ins[][]=new int[9][9];
                for (int i1=0;i1<81;i1++){
                    int i=i1/9;
                    int j=i1%9;
                    if (btn[i1].getText()!="")
                    {
                        ins[i][j] = Integer.parseInt(btn[i1].getText().toString());
                    }
                }
                DensityUtil solvert = new DensityUtil();
                for (int i=0;i<9;i++)
                    for (int j=0;j<9;j++)
                        outs[i][j].removeAllElements();
                totolresnum = 0;
                boolean rest = solvert.solvesuku(ins,outs);
                if(!rest){
                    new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题
                            .setMessage("您所输入的数据无解，请修改后再试！")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    // TODO Auto-generated method stub
                                    //finish();
                                }
                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            // TODO Auto-generated method stub
                        }
                    }).show();//在按键响应事件中显示此对话框
                    return;
                }
                for (int i1 = 0; i1 < 81; i1++) {
                    int i = i1 / 9;
                    int j = i1 % 9;
                    btn[i1].setText(String.format("%d", outs[i][j].get(0)));
                }
                cursel = 0;
                TextView curshow = (TextView)findViewById(R.id.cursel);
                curshow.setText(String.format("%d",cursel+1));
                int numres = outs[0][0].size();
                totolresnum = numres;
                TextView resnums = (TextView)findViewById(R.id.textResNum);
                resnums.setText(String.format("总共有%d个答案",numres));
            }
        });
        RelativeLayout activity_main = (RelativeLayout) findViewById(R.id.fragment);
        activity_main.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent arg1) {
                GridLayout tmp9gridLayout = (GridLayout)findViewById(R.id.grid9);
                tmp9gridLayout.setVisibility(View.INVISIBLE);
                if (curBtn!=null)
                {
                    curBtn.setText("");
                    curBtn.setBackgroundColor(Color.rgb(100, 100, 100));
                }
                curBtn = null;
                return false;
            }
        });

        // Inflate the menu; this adds items to the action bar if it is present.
        GridLayout tmp9gridLayout = (GridLayout)findViewById(R.id.grid9);
        tmp9gridLayout.setVisibility(View.INVISIBLE);
        final GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for (int i=0;i<81;i++)
        {
            btn[i]=new Button(this);
            int wid = DensityUtil.dip2px(this, 31);
            int het = DensityUtil.dip2px(this, 31);

            btn[i].setWidth(wid);
            btn[i].setHeight(het);
            btn[i].setText("");
            btn[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            btn[i].setBackgroundColor(Color.rgb(100, 100, 100));
            btn[i].setTextColor(Color.rgb(0, 0, 0));
            btn[i].setPadding(0, 0, 0, 0);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curBtn = (Button)v;
                    GridLayout tmp9gridLayout = (GridLayout)findViewById(R.id.grid9);
                    GridLayout tmp81grid = (GridLayout)findViewById(R.id.gridLayout);
                    int wid = DensityUtil.dip2px(v.getContext(), 31);
                    int het = DensityUtil.dip2px(v.getContext(), 31);
                    int widh = DensityUtil.dip2px(v.getContext(), 96);
                    int onedp = DensityUtil.dip2px(v.getContext(), 1);
                    int ptop = v.getTop();
                    int pleft = v.getLeft();
                    int partop = tmp81grid.getTop();
                    int parleft = tmp81grid.getLeft();
                    RelativeLayout.LayoutParams g9params =new RelativeLayout.LayoutParams(widh,widh);
                    g9params.leftMargin = parleft+pleft-wid-3*onedp;
                    g9params.topMargin = partop+ ptop-het-3*onedp;
                    tmp9gridLayout.setLayoutParams(g9params);
                    tmp9gridLayout.setVisibility(View.VISIBLE);

                    Button btn[] = new Button[9];
                    for (int i=0;i<9;i++) {
                        btn[i] = new Button(v.getContext());
                        btn[i].setWidth(wid);
                        btn[i].setHeight(het);
                        btn[i].setText(String.format("%d",i+1));
                        btn[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                        btn[i].setBackgroundColor(Color.rgb(150, 150, 150));
                        btn[i].setTextColor(Color.rgb(0, 255, 0));
                        btn[i].setPadding(0, 0, 0, 0);
                        btn[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (curBtn!=null){
                                    curBtn.setText(((Button)v).getText());
                                    curBtn.setBackgroundColor(Color.rgb(10, 100, 250));
                                    curBtn = null;
                                }
                                GridLayout tmp9gridLayout = (GridLayout)findViewById(R.id.grid9);
                                tmp9gridLayout.setVisibility(View.INVISIBLE);
                            }
                        });
                        //指定该组件所在的行
                        GridLayout.Spec rowSpec = GridLayout.spec(i / 3);
                        //指定该组件所在的列
                        GridLayout.Spec columnSpec = GridLayout.spec(i % 3);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.width = wid;
                        params.height = het;
                        int margin = DensityUtil.dip2px(v.getContext(), 1);
                        params.setMargins(margin, margin, margin, margin);
                        tmp9gridLayout.addView(btn[i], params);
                    }
                }
            });
            //指定该组件所在的行
            GridLayout.Spec rowSpec = GridLayout.spec(i/9);
            //指定该组件所在的列
            GridLayout.Spec columnSpec = GridLayout.spec(i%9);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,columnSpec);
            params.width = wid;
            params.height = het;
            int margin = DensityUtil.dip2px(this, 1);
            params.setMargins(margin, margin, margin, margin);
            gridLayout.addView(btn[i], params);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
