package cn.goo00d.suku;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class DensityUtil {
    public static int oneline[]={1,2,3,4,5,6,7,8,9};


    public Vector<Integer> [][]res=new Vector[9][9];
    public boolean fish = false;
    public int totoljie = 0;
    public int totolnum = 0;
    public DensityUtil()
    {
        for (int i=0;i<9;i++)
            for (int j=0;j<9;j++)
                res[i][j]=new Vector();
        fish = false;
        totoljie = 0;
        totolnum = 0;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static void deepcopyv(Vector<Integer> [][]src,Vector<Integer>[][]dest)
    {
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                dest[i][j]=new Vector();
            }
        }
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                for (Integer k:src[i][j])
                {
                    dest[i][j].add(k);
                }
            }
        }
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static void prepareprec(Vector<Integer>[][] nines) {
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if (nines[i][j].size()==1)
                    continue;
                Vector<Integer> tmp = new Vector<Integer>();
                for (int i1 = 0;i1<9;i1++){
                    tmp.add(oneline[i1]);
                }

                for (int k=0;k<9;k++) {
                    if (k != j && nines[i][k].size() == 1) {
                        if (tmp.contains(nines[i][k].get(0))) {
                            tmp.remove(nines[i][k].get(0));
                        }
                    }
                    if (k != i && nines[k][j].size() == 1) {
                        if(tmp.contains( nines[k][j].get(0) )) {
                            tmp.remove(nines[k][j].get(0));
                        }
                    }
                }
                int pi = i / 3;
                int pj = j / 3;
                int qi = i % 3;
                int qj = j % 3;
                pi *= 3;
                pj *= 3;
                for(int xi=0;xi<3;xi++) {
                    for(int xj=0;xj<3;xj++) {
                        if(xi != qi && xj!=qj) {
                            if(nines[pi + xi][pj + xj].size() == 1) {
                                Integer ts = nines[pi + xi][pj + xj].get(0);
                                if(tmp.contains(ts)) {
                                    tmp.remove(ts);
                                }
                            }
                        }
                    }
                }
                nines[i][j] = tmp;
            }
        }
    }


    public static boolean precheck(Vector<Integer>[][] nines) {
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if (nines[i][j].size()==0)
                {
                    continue;
                }
                for (int k=0;k<9;k++) {
                    if(k != j && nines[i][k].size() == 1) {
                        if(nines[i][k].get(0) == nines[i][j].get(0))
                            return false;
                    }
                    if(k != i && nines[k][j].size() == 1) {
                        if (nines[k][j].get(0) == nines[i][j].get(0))
                            return false;
                    }
                }
                int pi = i / 3;
                int pj = j / 3;
                int qi = i % 3;
                int qj = j % 3;
                pi *= 3;
                pj *= 3;
                for (int xi=0;xi<3;xi++){
                    for (int xj=0;xj<3;xj++){
                        if(xi != qi && xj!=qj) {
                            if(nines[pi + xi][pj + xj].size() == 1) {
                                Integer ts = nines[pi + xi][pj + xj].get(0);
                                if(ts == nines[i][j].get(0)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    public static void proct(Vector<Integer>[][] nines) {
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if (nines[i][j].size()==1)
                    continue;
                Vector<Integer> tmp = new Vector<Integer>();
                for (int i1 = 0;i1<nines[i][j].size();i1++){
                    tmp.add(nines[i][j].get(i1));
                }

                for (int k=0;k<9;k++) {
                    if (k != j && nines[i][k].size() == 1) {
                        if (tmp.contains(nines[i][k].get(0))) {
                            tmp.remove(nines[i][k].get(0));
                        }
                    }
                    if (k != i && nines[k][j].size() == 1) {
                        if(tmp.contains( nines[k][j].get(0) )) {
                            tmp.remove(nines[k][j].get(0));
                        }
                    }
                }
                int pi = i / 3;
                int pj = j / 3;
                int qi = i % 3;
                int qj = j % 3;
                pi *= 3;
                pj *= 3;
                for(int xi=0;xi<3;xi++) {
                    for(int xj=0;xj<3;xj++) {
                        if(xi != qi && xj!=qj) {
                            if(nines[pi + xi][pj + xj].size() == 1) {
                                Integer ts = nines[pi + xi][pj + xj].get(0);
                                if(tmp.contains(ts)) {
                                    tmp.remove(ts);
                                }
                            }
                        }
                    }
                }
                nines[i][j] = tmp;
            }
        }
    }
    public static boolean isfinish(Vector<Integer>[][] nines) {
        for (int i=0;i<9;i++)
            for (int j=0;j<9;j++)
                if(nines[i][j].size()!= 1)
                    return false;
        return true;
    }
    public static void findres(Vector<Integer>[][] nines) {
        for(int i=0;i<11;i++) {
            proct(nines);
            if(isfinish(nines))
            return;
        }
    }
    public static boolean checkfalse(Vector<Integer>[][] nines) {
        for (int i=0;i<9;i++)
            for (int j=0;j<9;j++)
                if (nines[i][j].size()==0)
                    return true;
        return false;
    }
    public static void printres(Vector<Integer>[][] nines) {
        for(int i=0;i<9;i++) {
            for (int j=0;j<9;j++)
            System.out.println(nines[i][j]);
        }
        System.out.println("\n");
    }


    public void solve(Vector<Integer>[][] nines) {
        totoljie += 1;
        if(fish)
            return;
        findres(nines);
        if(checkfalse(nines))
            return;
        if(isfinish(nines)) {
            totolnum += 1;
            printres(nines);
            System.out.print("totoal num" + totolnum + " " + totoljie);
            for (int i=0;i<9;i++)
                for (int j=0;j<9;j++)
                {
                    res[i][j].add(nines[i][j].get(0));
                }
            if (totolnum>1 && totolnum>9)
            fish = true;
           return;
        }
        int finds = 0;
        int minindex = 0;
        int minvalue = 100;
        for (int i=0;i<9;i++)
        {
            for (int j=0;j<9;j++){
                int lenij = nines[i][j].size();
                if(lenij > 1 && lenij<10 && lenij<minvalue) {
                    minindex = i * 10 + j;
                    minvalue = lenij;
                }
            }
        }
        int i = minindex / 10;
        int j = minindex % 10;
        for(int k=0;k<nines[i][j].size();k++) {
            if (fish) {
                return;
            }
            Vector<Integer>[][] ninest = new Vector[9][9];
            deepcopyv(nines, ninest);
            ninest[i][j].removeAllElements();
            ninest[i][j].add(nines[i][j].get(k));
            solve(ninest);
        }
    }


    public boolean solvesuku(int ninesin[][],Vector<Integer> outs[][]) {
        Vector [][]nines = new Vector[9][9];
        for (int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                nines[i][j] = new Vector();
            }
        }
        for (int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
            if (ninesin[i][j] != 0) {
                nines[i][j].add(ninesin[i][j]);
            }
            }
        }
        if (!precheck (nines)) {
            System.out.println("无解");
            return false;
        }
        prepareprec(nines);
        findres(nines);
        solve(nines);
        for (int i=0;i<9;i++)
            for (int j=0;j<9;j++)
                for (Integer k:res[i][j])
                outs[i][j].add(k);
//        int hh = 1;
//        for (int i=0;i<9;i++) {
//            for (int j=0;j<9;j++) {
//                hh *= nines[i][j].size();
//            }
//        }
//        for (int i=0;i<9;i++) {
//            for (int j=0;j<9;j++) {
//                outs[i][j] = res[i][j].get(0);
//            }
//        }
        return true;
    }
}