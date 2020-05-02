
//import java.util.*;
//import percolation.Percolation;
//import package Percolation;
public class PercolationStats {
	
	private final int a;
	private double sd;
	private final double[] opened_sites_mean;
	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
//		sum=0;
		if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N and T must be > 0");
        }
		opened_sites_mean=new double[trials];
		a=trials;
//    System.out.print("kyuchu");
	    for(int v=0;v<a;v++) {
	    	Percolation trial=new Percolation(n);
//	    	System.out.print("t"+v);
	//    	trial[v]=new Percolation(n);
	//    	System.out.print("root called");
	    	while(!trial.percolates()) {
//	    		System.out.print("root called");
	    		int id_opened_row=(int)Math.floor((Math.random()*(n)))+1;
	    		int id_opened_col=(int)Math.floor((Math.random()*(n)))+1;
	    		if(!trial.isOpen(id_opened_row, id_opened_col)) {
//	    			System.out.print("op"+id_opened_row+" "+id_opened_col+"\n");
	    			trial.open(id_opened_row, id_opened_col);
//	    			connection_maker(id_opened_row, id_opened_col);
	    			}
	    	}
	    	opened_sites_mean[v]=((double)trial.numberOfOpenSites())/(n*n);
//	    	sum+=opened_sites_mean[v];
//	    	System.out.print("open count:"+opened_sites_mean[v]+"\n");
	    }
	}

    // sample mean of percolation threshold
public double mean() {
	double sum=0;
	for (int i = 0; i < opened_sites_mean.length; i++) {
        sum += opened_sites_mean[i];
    }
	return sum/(a);}

    // sample standard deviation of percolation threshold
public double stddev() {
	double sum_s=0;
	for(int i=0;i<opened_sites_mean.length;i++) {
		sum_s+=Math.pow(opened_sites_mean[i]-mean(),2);
	}
	if(a!=1)
	sd=Math.sqrt(sum_s/(a-1));
	return sd;}

    // low endpoint of 95% confidence interval
public double confidenceLo() {return mean()-(1.96*sd/Math.sqrt(a+1));}

    // high endpoint of 95% confidence interval
public double confidenceHi() {return mean()+(1.96*sd/Math.sqrt(a+1));}

   // test client (see below)
public static void main(String[] args) {
	int gridSize=10;
	int trialCount=10;
	if (args.length >= 2) {
        gridSize = Integer.parseInt(args[0]);
        trialCount = Integer.parseInt(args[1]);
    }
    PercolationStats ele = new PercolationStats(gridSize, trialCount);
//	System.out.print("yoyo\n");
//	PercolationStats ele=new PercolationStats(n, trys);
	System.out.print("mean			= "+ele.mean()+"\n");
	System.out.print("stdsev			= "+ele.stddev()+"\n");
	System.out.print("95% confidence interval	=["+ele.confidenceLo()+","+ele.confidenceHi()+"]\n");
}
}
