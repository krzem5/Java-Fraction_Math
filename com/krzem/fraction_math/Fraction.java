package com.krzem.fraction_math;



import java.lang.Math;



public class Fraction{
	private long w;
	private long n;
	private long dn;
	private boolean neg=false;



	public Fraction(Fraction f){
		this.set(f.w*(f.neg?-1:1),f.n,f.dn);
	}
	public Fraction(Long... p){
		this._set(p);
	}



	public Fraction set(Fraction f){
		this.set(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction set(Long... p){
		this._set(p);
		return this;
	}



	public Fraction add(Fraction f){
		this.add(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction add(Long... p){
		Fraction f2=new Fraction();
		f2._set(p);
		long w=this.w+f2.w;
		long dn=this._lcm(this.dn,f2.dn);
		long n=this.n*(dn/this.dn)+f2.n*(dn/f2.dn);

		this.set(w,n,dn);
		return this;
	}



	public Fraction sub(Fraction f){
		this.sub(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction sub(Long... p){
		Fraction f2=new Fraction();
		f2._set(p);
		long w=this.w-f2.w;
		long dn=this._lcm(this.dn,f2.dn);
		long n=this.n*(dn/this.dn)-f2.n*(dn/f2.dn);
		while (w>0&&n<0){
			w--;
			n+=dn;
		}
		this.set(w,n,dn);
		return this;
	}



	public Fraction mult(Fraction f){
		this.mult(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction mult(Long... p){
		Fraction f2=new Fraction();
		f2._set(p);
		long n1=this.w*this.dn+this.n;
		long dn1=this.dn+0;
		long n2=f2.w*f2.dn+f2.n;
		long dn2=f2.dn+0;
		long gcd1=this._gcd(n1,dn2);
		n1/=gcd1;
		dn2/=gcd1;
		long gcd2=this._gcd(n2,dn1);
		n2/=gcd2;
		dn1/=gcd2;
		this.set(n1*n2*(this.neg^f2.neg?-1:1),dn1*dn2);
		return this;
	}



	public Fraction div(Fraction f){
		this.div(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction div(Long... p){
		Fraction f2=new Fraction();
		f2._set(p);
		long n1=this.w*this.dn+this.n;
		long dn1=this.dn+0;
		long n2=f2.dn+0;
		long dn2=f2.w*f2.dn+f2.n;
		long gcd1=this._gcd(n1,dn2);
		n1/=gcd1;
		dn2/=gcd1;
		long gcd2=this._gcd(n2,dn1);
		n2/=gcd2;
		dn1/=gcd2;
		this.set(n1*n2*(this.neg^f2.neg?-1:1),dn1*dn2);
		return this;
	}



	public Fraction power(Fraction f){
		this.power(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction power(Long... p){
		Fraction f2=new Fraction();
		f2._set(p);
		double v=(double)(f2.w*f2.dn+f2.n)/f2.dn;
		double n=Math.pow(this.w*this.dn+this.n,v);
		double dn=Math.pow(this.dn,v);
		int pad=(int)Math.pow(10,Math.max(Double.toString(n%1).length(),Double.toString(dn%1).length()));
		n*=pad;
		dn*=pad;
		this.set((long)n,(long)dn);
		return this;
	}



	public Fraction root(Fraction f){
		this.root(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction root(Long... p){
		Fraction f2=new Fraction();
		f2._set(p);
		double v=(double)(f2.w*f2.dn+f2.n)/f2.dn;
		double n=Math.pow(this.w*this.dn+this.n,1d/v);
		double dn=Math.pow(this.dn,1d/v);
		int pad=(int)Math.pow(10,Math.max(Double.toString(n%1).length(),Double.toString(dn%1).length()));
		n*=pad;
		dn*=pad;
		this.set((long)n,(long)dn);
		return this;
	}



	public Fraction log(Fraction f){
		this.log(f.w*(f.neg?-1:1),f.n,f.dn);
		return this;
	}
	public Fraction log(Long... p){
		Fraction f2=new Fraction();
		f2._set(p);
		double v=Math.log((double)(this.w*this.dn+this.n)/this.dn)/Math.log((double)(f2.w*f2.dn+f2.n)/f2.dn);
		int pad=(int)Math.pow(10,Double.toString(v%1).length());
		v*=pad;
		this.set((long)v,(long)pad);
		return this;
	}



	@Override
	public String toString(){
		return String.format("com.krzem.fraction_math.Fraction(whole=%s%d, numerator=%d, denominator=%d)",(this.neg==true?"-":""),this.w,this.n,this.dn);
	}



	private void _set(Long[] p){
		if (p.length==0){
			this.set(0L,0L,1L);
		}
		else if (p.length==1){
			this.set(p[0],0L,1L);
		}
		else if (p.length==2){
			this.set(0L,p[0],p[1]);
		}
		else if (p.length==3){
			this.neg=false;
			this.w=p[0];
			this.n=p[1];
			this.dn=p[2];
			if (this.dn==0){
				throw new ArithmeticException("Denominator can't be equal to zero!");
			}
			this._shorten();
		}
		else{
			throw new IllegalArgumentException("Too many arguments!");
		}
	}



	private void _shorten(){
		int ng=(this.neg==true?1:0);
		if (this.w<0){
			ng++;
			this.w*=-1;
		}
		if (this.n<0){
			ng++;
			this.n*=-1;
		}
		if (this.dn<0){
			ng++;
			this.dn*=-1;
		}
		this.neg=(ng%2==1?true:false);
		this.w+=this.n/this.dn;
		this.n-=this.n/this.dn*this.dn;
		if (this.n!=0){
			long gcd=this._gcd(this.n,this.dn);
			this.n/=gcd;
			this.dn/=gcd;
		}
		else{
			this.dn=1;
		}
	}



	private long _gcd(long a,long b){
		while (b!=0){
			long t=b+0;
			b=a%b;
			a=t;
		}
		return a;
	}



	private long _lcm(long a,long b){
		return a*b/this._gcd(a,b);
	}
}