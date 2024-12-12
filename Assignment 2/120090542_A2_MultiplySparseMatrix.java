
import java.util.*;

public class MultiplySparseMatrix {
    

    static class Node{
        Node next;
        int num;
        int col;
        Node(int d,int c){
            next=null;
            num=d;
            col=c;
        }
    }
    
    static class LinkedList1{
        Node head=null;
        Node tail=null;
        LinkedList1(Node h,Node t){
            head=h;
            tail=t;
        }
    
        public void addNode(int num, int col){
            Node current=new Node(num,col);
            if (this.head==null){
                this.head=current;
                this.tail=current;
            }else if (this.head.next==null){
                this.head.next=current;
                this.tail.next=current;
                this.tail=current;
            }
            else{
                this.tail.next=current;
                this.tail=current;
            }
        }
    }
    
    static class SparseMatrix{
        int row=0;
        int col=0;
        LinkedList1[] array;
        SparseMatrix(int r,int c){
            row=r;
            col=c;
            array=new LinkedList1[r];
            for (int i=0;i<row;i++){
                Node head=null;
                Node tail=null;
                LinkedList1 r1=new LinkedList1(head, tail);
                array[i]=r1;
            }
        }
    }
    
    public static SparseMatrix transpose(SparseMatrix m){
        SparseMatrix result=new SparseMatrix(m.col, m.row);
        result.row=m.col;
        result.col=m.row;
        for (int i=0;i<m.row;i++){
            Node current=m.array[i].head;
            while (current != null){
                int n=current.num;
                int col=current.col;
                result.array[col-1].addNode(n,i+1);
                current=current.next;
            }
        }
        return result;
    }

    public static int dotProduct(LinkedList1 vec1, LinkedList1 vec2){
        int sum=0;
        
        Node c1=vec1.head;
        Node c2=vec2.head;

        while (c1!=null && c2!= null){
            int col1=c1.col;
            int col2=c2.col;

            if (col1==col2){
                sum+=c1.num*c2.num;
                c1=c1.next;
                c2=c2.next;
            }else if (col1>col2){
                c2=c2.next;
            }else{
                c1=c1.next;
            }
        }


        return sum;
    }

    public static LinkedList1 rowTmatrix(LinkedList1 vec, SparseMatrix m){
        Node head=null;
        Node tail=null;
        LinkedList1 result=new LinkedList1(head, tail);
        SparseMatrix mm=transpose(m);

        
        for (int i=0;i<m.col;i++){
            int sum=0;
            Node current=vec.head;
            
            sum=dotProduct(vec, mm.array[i]);

            if (sum!=0){
                result.addNode(sum, i+1);
            }

            }
        

        return result;
    }

    public static SparseMatrix multiply_matrix(SparseMatrix m1, SparseMatrix m2){
        SparseMatrix result=new SparseMatrix(m1.row, m2.col);
        for (int i=0;i<m1.row;i++){
            result.array[i]=rowTmatrix(m1.array[i],m2);
        }
        result.row=m1.row;
        result.col=m2.col;
        return result;  
    }
    
    public static void printMatrix(SparseMatrix m){
        System.out.println(Integer.toString(m.row)+", "+Integer.toString(m.col));
        
        for (int i=0;i<m.row;i++){
            
            Node current=m.array[i].head;
            System.out.print(i+1);
            System.out.print(" ");
            
            if (current == null){
                System.out.println(":");
            }
            else{
                while (current != null){
                    System.out.print(Integer.toString(current.col)+":"+Integer.toString(current.num)+" ");
                    current=current.next;
                }
                System.out.println();
            }
    
            
        }
    }
    
    public static Node getNode(String str){
        int mid=str.indexOf(":");
        int col=Integer.valueOf(str.substring(0,mid));
        int num=Integer.valueOf(str.substring(mid+1));
        Node current_node=new Node(num, col);
        return current_node;
    }
    
    public static LinkedList1 getRow(String str){
        LinkedList1 l=new LinkedList1(null, null);
        String valid=str.substring(str.indexOf(" ")+1);
            if (valid.equals(":")){
                return l;
            }
            else if (valid.indexOf(" ")==-1){
                Node current_node=getNode(valid);
                l.addNode(current_node.num, current_node.col);
                return l;
            }
            else{
                String[] g=valid.split(" ");
                for (int i=0;i<g.length;i++){
                    Node current_node=getNode(g[i]);
                    l.addNode(current_node.num, current_node.col);
                }
                return l;
            }
    
    }
    
    public static SparseMatrix[] getMatrix(){
        SparseMatrix[] result=new SparseMatrix[2];
        Scanner input=new Scanner(System.in);
        
        String first_row=input.nextLine();
        int row1=Integer.valueOf(first_row.substring(0,first_row.indexOf(",")));
        int col1=Integer.valueOf(first_row.substring(first_row.indexOf(" ")+1));
        SparseMatrix m1=new SparseMatrix(row1,col1);

        for (int n=0;n<row1;n++){
            m1.array[n]=getRow(input.nextLine());
        }

        String next_row=input.nextLine();
        int row2=Integer.valueOf(next_row.substring(0,next_row.indexOf(",")));
        int col2=Integer.valueOf(next_row.substring(next_row.indexOf(" ")+1));
        SparseMatrix m2=new SparseMatrix(row2,col2);

        for (int n=0;n<row2;n++){
            m2.array[n]=getRow(input.nextLine());
        }
        result[0]=m1;
        result[1]=m2;
            
    
    return result;
    }
    
    public static void main(String[] args) {
        SparseMatrix[] m=getMatrix();
        SparseMatrix m1=m[0];
        SparseMatrix m2=m[1];
        SparseMatrix result=multiply_matrix(m1, m2);    
        printMatrix(result) ;
    }
    }
    