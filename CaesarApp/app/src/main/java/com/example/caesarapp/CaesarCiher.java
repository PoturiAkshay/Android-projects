package com.example.caesarapp;

public class CaesarCiher {

    private Integer ShiftAmount;

    public CaesarCiher(int ShiftAmount)
    {
        this.ShiftAmount=ShiftAmount;
    }

    public String translate(String input)
    {
        String EncryptedInput="";
        char letter;
        int ascii;
        for (int i=0; i<input.length(); i++)
        {
            letter=input.charAt(i);

            if(letter!=' ')
            {
                ascii=(int) letter;
                ascii=ascii+(this.ShiftAmount%26);
                ascii=ascii > 'Z' ? ascii-26 : ascii;
                EncryptedInput=EncryptedInput+(char) ascii;
            }
            else
            {
                EncryptedInput=EncryptedInput+' ';
            }
        }
        return EncryptedInput;
    }
}
