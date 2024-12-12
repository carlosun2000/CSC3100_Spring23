
import java.util.*;

public class TestMathExpr {

    public static boolean isSV(String str){
        int s=str.indexOf("sin",0);
        while(s!=-1){
            if (str.charAt(s+3)!='(') return false;
            s=str.indexOf("sin",s+3);
        }
        int c=str.indexOf("cos",0);
        while(c!=-1){
            if (str.charAt(c+3)!='(') return false;
            c=str.indexOf("cos",c+3);
        }
        int t=str.indexOf("tan",0);
        while(t!=-1){
            if (str.charAt(t+3)!='(') return false;
            t=str.indexOf("tan",t+3);
        }
        int sq=str.indexOf("sqrt",0);
        while(sq!=-1){
            if (str.charAt(sq+4)!='(') return false;
            sq=str.indexOf("sqrt",sq+4);
        }
        
        return true;
    }

    public static boolean isVP(String str){
        int before=str.indexOf('(',1);
        while (before !=-1){
            int num=str.charAt(before-1);
            if (num>=48 && num<=57) return false;
            before=str.indexOf('(',before+1);
        }
        int after=str.indexOf(')',1);
        while (after !=-1 && after !=str.length()-1){
            int num=str.charAt(after+1);
            if (num>=48 && num<=57) return false;
            after=str.indexOf(')',after+1);
        }
        return true;
    }

    public static boolean isValid(String str){
        for (int i=0;i<str.length();i++){
            int pos=str.charAt(i);
            if (pos==42 || pos==43 || pos==45 || pos==47){
                if (str.charAt(i+1)=='+' ||str.charAt(i+1)=='-'||str.charAt(i+1)=='*'||str.charAt(i+1)=='/')return false;
            }
        }
        return true;
    }

    public static boolean isPositive(String str){
        char chr=str.charAt(0);
 
        return (chr!='-');
    }

    public static boolean isNumeric(String str){
        if (!isPositive(str)) str=str.substring(1);
        boolean flag=true;
        for (int i=1;i<str.length();i++){
            int chr=str.charAt(i);
            if ((chr<48 || chr>57) && chr!=46){

                flag=false;
            }
        }
        return flag;
    }

    public static String add(String str){
        boolean flag=false;
        if (!isPositive(str)) {str=str.substring(1);flag=true;}
        int index=str.indexOf('+');
        double num1=Double.parseDouble(str.substring(0,index ));
        double num2=Double.parseDouble(str.substring(index+1));
        
        if (flag) return String.valueOf(-num1+num2);
        return String.valueOf(num1+num2);
    }

    public static String minus(String str){
        boolean flag=false;
        if (!isPositive(str)) {str=str.substring(1);flag=true;}
        int index=str.indexOf('-');
        if (index==0){
            index=str.indexOf('-',1);
        }
        if (index==-1){
            if (flag) return String.valueOf(-Double.parseDouble(str));
            else return str;
        }
        double num1=Double.parseDouble(str.substring(0,index ));
        double num2=Double.parseDouble(str.substring(index+1));
        if (flag) return String.valueOf(-num1-num2);
        return String.valueOf(num1-num2);
    }

    public static String times(String str){
        boolean flag=false;
        if (!isPositive(str)) {str=str.substring(1);flag=true;}
        int index=str.indexOf('*');
        double num1=Double.parseDouble(str.substring(0,index ));
        double num2=Double.parseDouble(str.substring(index+1));
        if (flag) return String.valueOf(-num1*num2);
        return String.valueOf(num1*num2);
    }

    public static String divide(String str){
        boolean flag=false;
        if (!isPositive(str)) {str=str.substring(1);flag=true;}
        int index=str.indexOf('/');
        double num1=Double.parseDouble(str.substring(0,index ));
        double num2=Double.parseDouble(str.substring(index+1));
        if (flag) return String.valueOf(-num1/num2);
        return String.valueOf(num1/num2);
    }

    public static boolean isSimple(String str){
        for (int i=0;i<str.length();i++){
            int pos=str.charAt(i);
            if (pos<40 || pos>57 || pos==44){
                return false;
            }
        }
        return true;
    }
   
    public static String EvaluateSimple(String str){
        while (true){
            String k;
            int op=0;
            int next=0;
            int start=0;
            boolean pls=true;
            boolean flag=false;
            if (!isPositive(str)) {start=1;}
            int firstp=str.indexOf('+',start);
            int firstm=str.indexOf('-',start);
            if (firstp==-1 && (firstm==-1 || firstm==0)) return str;

            if (firstp==-1 || firstm==-1){
                op=Math.max(firstp,firstm);
            }
            else{
                op=Math.min(firstp,firstm);
            }

            if (op==firstm){
                pls=false;
            }

            int nextp=str.indexOf('+',op+1);
            int nextm=str.indexOf('-',op+2);

            if (nextp==-1 && nextm==-1){
                if (pls) str=add(str);
                else str=minus(str);
                continue;
            }
            else if (nextp==-1 || nextm==-1){
                next=Math.max(nextp,nextm);
            }
            else{
                next=Math.min(nextp,nextm);
            }
            
            String tmp=str.substring(0, next);
            if (pls) k=add(tmp);
            else k=minus(tmp);
            str=k+str.substring(next);
        }
    }

    public static String EvaluateT(String str){
        while (true){
            
            boolean t=true;
            int op=0;
            int before=0;
            int after=0;
            int start=0;
            int next=0;

            int firstp=str.indexOf('+',start);
            int firstm=str.indexOf('-',start);

            if (!isPositive(str)) {start=1;}

            int firstt=str.indexOf('*',start);
            int firstd=str.indexOf('/',start);
            if (firstt==-1 && firstd==-1 ) return str;

            if (firstt==-1 || firstd==-1){
                op=Math.max(firstt,firstd);
            }
            else{
                op=Math.min(firstt,firstd);
            }
            if (op==firstd){
                t=false;
            }

            for (int i=1;i<op;i++){
                int pos=str.charAt(i);
                if ((pos<48 || pos>57) &&pos!=46){
                    before=i;
            
            }
            }    
            int nextp=str.indexOf('+',op+1);
            int nextm=str.indexOf('-',op+1);
            int nextt=str.indexOf('*',op+1);
            int nextd=str.indexOf('/',op+1);
            if (nextm==op+1)nextm=str.indexOf('-',op+2);

            int operators[]={nextp,nextm,nextt,nextd};
            if (nextp==-1 && nextm==-1 && nextt==-1 && nextd==-1){
                
                next=str.length();
            }

            else {
                next=str.length();
                for (int i=0;i<4;i++){
                    if (operators[i]!=-1){
                        if (operators[i]<next){
                            next=operators[i];
                        }
                    }
                }
            }

            String s;

            if (before==0){
                if(t){
                    s=times(str.substring(before,next));
                }else{
                    s=divide(str.substring(before,next));
                }
            }
            else{
                if(t){
                    s=times(str.substring(before+1,next));
                }else{
                    s=divide(str.substring(before+1,next));
                }
            }
            
            int bf[]={firstp,firstm,firstt,firstd};
            int tmp=op;
            for (int i=0;i<4;i++){
                if (bf[i]!=-1){
                    if (bf[i]<tmp){
                        tmp=bf[i];
                    }
                }
            }
            String str1;
            if (tmp!=op){
                str1=str.substring(0,before+1);
            }
            else{
                str1="";
            }
            
            String str2=str.substring(next);
            if(start==1 && str1.length()==1) {str1="";}

            str=str1+s+str2;
        }
    }

    public static String EvaluateP(String str){
        String before;
        String after;
        while (str.indexOf('(')!=-1){
            int begin=str.indexOf('(');
            int end=str.indexOf(')');
            for (int i=begin;i<end;i++){
                if (str.charAt(i)=='('){
                    begin=i;
                }
            }
            if (begin==0){
                before="";
            }else{
                before=str.substring(0, begin);
            }
            
            String mid=str.substring(begin+1, end);
            String exp=EvaluateSimple(EvaluateT(mid));
            if (end==str.length()-1){
                after="";
            }else{
                after=str.substring(end+1);
            }
            str=before+exp+after;
        }

        return str;
    }

    public static String Esin(String str){
        int index=str.indexOf("sin");
        index+=3;
        String tmp=str.substring(index);
        String k=EvaluateSimple(EvaluateT(EvaluateP(tmp)));
        str=String.valueOf(Math.sin(Double.parseDouble(k)));
        return str;
    }

    public static String Ecos(String str){
        int index=str.indexOf("cos");
        index+=3;
        String tmp=str.substring(index);
        String k=EvaluateSimple(EvaluateT(EvaluateP(tmp)));
        str=String.valueOf(Math.cos(Double.parseDouble(k)));
        return str;
    }

    public static String Etan(String str){
        int index=str.indexOf("tan");
        index+=3;
        String tmp=str.substring(index);
        String k=EvaluateSimple(EvaluateT(EvaluateP(tmp)));
        str=String.valueOf(Math.tan(Double.parseDouble(k)));
        return str;
    }

    public static String Esqrt(String str){
        int index=str.indexOf("sqrt");
        index+=4;
        String tmp=str.substring(index);
        String k=EvaluateSimple(EvaluateT(EvaluateP(tmp)));
        if(Double.parseDouble(k)<0){
            k="";
        }
        str=String.valueOf(Math.sqrt(Double.parseDouble(k)));
        return str;
    }

    public static int LocateEnd(int current,String str){
        int n=0;
        int n2=0;
        int len=0;
        for (int i=current;i<str.length();i++){
            len+=1;
            if (str.charAt(i)=='(') n+=1;
            if (str.charAt(i)==')') {
                n2+=1; 
                if (n2==n)break;}
        }

        return len+current;
    }

    public static String Evaluate(String str){
        while(!isSimple(str)){
            String str1;
            String str2;
            boolean flag=false;

            int pos_sin=str.indexOf("sin");
            while (pos_sin !=-1){
                int current=pos_sin+3;
                int end=LocateEnd(current, str);
                String expression=str.substring(current, end);
                if (isSimple(expression)){
                    if (pos_sin==0){
                        str1="";
                    }else{
                        str1=str.substring(0,pos_sin);
                    }
                    if (end==str.length()){
                        str2="";
                    }else{
                        str2=str.substring(end);
                    }
                    String ex=str.substring(pos_sin, end);
                    String tmp=Esin(ex);
                    str=str1+tmp+str2;
                    flag=true;
                    break;
                }
                if (flag)continue;
                pos_sin=str.indexOf("sin",pos_sin+3);
            }

            int pos_cos=str.indexOf("cos");
            while (pos_cos !=-1){
                int current=pos_cos+3;
                int end=LocateEnd(current, str);
                String expression=str.substring(current, end);
                if (isSimple(expression)){
                    if (pos_cos==0){
                        str1="";
                    }else{
                        str1=str.substring(0,pos_cos);
                    }
                    if (end==str.length()){
                        str2="";
                    }else{
                        str2=str.substring(end);
                    }
                    String ex=str.substring(pos_cos, end);
                    String tmp=Ecos(ex);
                    str=str1+tmp+str2;
                    break;
                }
                pos_cos=str.indexOf("cos",pos_cos+3);
            }




            int pos_tan=str.indexOf("tan");

            while (pos_tan !=-1){
                int current=pos_tan+3;
                int end=LocateEnd(current, str);
                String expression=str.substring(current, end);
                if (isSimple(expression)){
                    if (pos_tan==0){
                        str1="";
                    }else{
                        str1=str.substring(0,pos_tan);
                    }
                    if (end==str.length()){
                        str2="";
                    }else{
                        str2=str.substring(end);
                    }
                    String ex=str.substring(pos_tan, end);
                    String tmp=Etan(ex);
                    str=str1+tmp+str2;
                    break;
                }
                pos_tan=str.indexOf("tan",pos_tan+3);
            }

            int pos_sqrt=str.indexOf("sqrt");
            while (pos_sqrt !=-1){
                int current=pos_sqrt+4;
                int end=LocateEnd(current, str);
                String expression=str.substring(current, end);
                if (isSimple(expression)){
                    if (pos_sqrt==0){
                        str1="";
                    }else{
                        str1=str.substring(0,pos_sqrt);
                    }
                    if (end==str.length()){
                        str2="";
                    }else{
                        str2=str.substring(end);
                    }
                    String ex=str.substring(pos_sqrt, end);
                    String tmp=Esqrt(ex);
                    str=str1+tmp+str2;
                    break;
                }
                pos_sqrt=str.indexOf("sqrt",pos_sqrt+4);
            }
        }
        String k=EvaluateSimple(EvaluateT(EvaluateP(str)));
        return k;
    }

    public static double parse(String str){
        str=str.replaceAll("\\s*","");
        String tmp;
        if (isValid(str) && isVP(str) &&isSV(str))tmp=Evaluate(str);
        else{tmp="";}
        if (isNumeric(tmp)) return Double.parseDouble(tmp);
        return 0.0d;
    }

    public static void main(String[] args) {

            Scanner input=new Scanner(System.in);
            while (input.hasNextLine()){
                try{
                    double result=parse(input.nextLine());
                    System.out.println(String.valueOf(Math.round(result)));
                
                }
                catch (Exception e1){System.out.println("invalid");}   

        }
        }
        
}