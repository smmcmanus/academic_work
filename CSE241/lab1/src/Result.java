package lab1;

public class Result {
    public XYPoint p1;
    public XYPoint p2;
    public double dist;
    
    Result(XYPoint ip1, XYPoint ip2, double d) {
    	if(ip1.x < ip2.x){
	   		p1 = ip1;
	   		p2 = ip2;
	   	}
	   	else if(ip1.x == ip2.x){
	   		if(ip1.y < ip2.y){
	   			p1 = ip1;
	   			p2 = ip2;
	   		}
	   		if(ip1.y > ip2.y){
	   			p1 = ip2;
	   			p2 = ip1;
	   		}
	   		if(ip1.y == ip2.y){
	   			p1 = ip1;
	   			p2 = ip2;
	   		}
	   	} 
	   	else{
	   		p1 = ip2;
	   		p2 = ip1;
	   	}
	   	dist = d;
    }

    public void print() {
	   Terminal.println(this.p1 + " " + this.p2 + " " + this.dist);
    }
}
