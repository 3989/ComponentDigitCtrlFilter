package com.tingjiandan.client.utlis;

import android.text.InputFilter;
import android.text.Spanned;

import com.easy.utils.KLog;

/**
 * 为了限制edit根据商品输入指定的位数和小数位
 */
public class ComponentDigitCtrlFilter implements InputFilter {

    private boolean isJPY;
    private int digit;

    public ComponentDigitCtrlFilter(boolean isJPY, int digit) {
        this.isJPY = isJPY;
        this.digit = digit;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        // 删除等特殊字符，直接返回
        if ("".equals(source.toString())) {
            return null;
        }
        String oriValue = dest.toString();
        StringBuffer sb = new StringBuffer(oriValue);
        sb.append(source);
        String newValue = sb.toString();
        String[] newValueVec = newValue.split("\\.");
        if (newValueVec.length == 2) {
            double number = Double.parseDouble(newValueVec[0]);
            boolean numberflag = true;
            if (isJPY) {
                numberflag = ((number - 9999999 > 0.000001) ? false : true);
            } else {
                numberflag = ((number - 99 > 0.000001) ? false : true);
            }

            boolean digitflag = true;
            try {
                String digitNumber = newValueVec[1];
                digitflag = digitNumber.toCharArray().length > digit ? false : true;
            } catch (Exception ex) {
                digitflag = false;
            }
            if (numberflag && digitflag) {
                return source;
            } else {
                return "";
            }
        } else {
            if (newValue.equals(".")) {
                return "";
            }

            KLog.e("newValueVec.length", newValueVec.length);
            KLog.e("newValueVec.length", newValue);

            if (getCount(newValue, ".") >= 2) {
                return source;
            } else {
                double value = Double.parseDouble(newValue);
                if (isJPY) {
                    return value > 9999999 ? "" : source;
                } else {
                    return value > 99 ? "" : source;
                }
            }
        }
        // dest.subSequence(dstart, dend)
    }

    public int getCount(String maxString, String minString) {
        // 定义一个统计变量，初始化值是0
        int count = 0;
        int index;
        //先查，赋值，判断
        while ((index = maxString.indexOf(minString)) != -1) {
            count++;
            maxString = maxString.substring(index + minString.length());
        }
        return count;
    }
}
