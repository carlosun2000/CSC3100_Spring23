
import java.util.*;

public class AddSparseMatrix {
    

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
                array[i]=null;
            }
        }
    }
    
    public static LinkedList1 addRow(LinkedList1 r1, LinkedList1 r2){
        
        Node head=null;
        Node tail=null;
        LinkedList1 result=new LinkedList1(head, tail);
        Node head1=r1.head;
        Node head2=r2.head;
    
        while(head1 != null && head2!=null){
            int col1=head1.col;
            int col2=head2.col;
            int sum=0;
            int c;
            if (col1==col2){
                sum=head1.num+head2.num;
                c=col1;
                head1=head1.next;
                head2=head2.next;
            }else if (col1<col2){
                sum=head1.num;
                c=col1;
                head1=head1.next;
            }else{
                sum=head2.num;
                c=col2;
                head2=head2.next;
            }
            if (sum!=0){
                result.addNode(sum,c);
            }
            
            
        }

        while (head2 != null){
            result.addNode(head2.num,head2.col);
            head2=head2.next;
            
        }

        while (head1 != null){
            result.addNode(head1.num,head1.col);
            head1=head1.next;
            
        }
    
        return result;
    }
    
    public static SparseMatrix add_matrix(SparseMatrix m1, SparseMatrix m2){
        int row_num=m1.row;
        SparseMatrix result=new SparseMatrix(row_num,m1.col);
        for (int i=0;i<row_num;i++){
            result.array[i]=addRow(m1.array[i],m2.array[i]);
        }
        result.row=m1.row;
        result.col=m1.col;
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
        int col2=Integer.valueOf(first_row.substring(next_row.indexOf(" ")+1));
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
        SparseMatrix result=add_matrix(m1, m2);    
        printMatrix(result) ;
    }
    }
    