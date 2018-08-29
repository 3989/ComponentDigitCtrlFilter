# ComponentDigitCtrlFilter
Android 限制edit根据商品输入指定的位数和小数位

使用方法：

int digit = 2;

editText.setFilters(new InputFilter[]{new ComponentDigitCtrlFilter(digit == 2, digit)});
