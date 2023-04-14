package com;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Delayed;

public class Common {
	
	
	public static CardType jugdeType(List<Card> list){
		
		int length=list.size();
		
		if(length<=4)
		{	
			if(list.size()>0&&Common.getValue(list.get(0))==Common.getValue(list.get(length-1)))
			{
				switch (length) {
				case 1:
					return CardType.c1;
				case 2:
					return CardType.c2;
				case 3:
					return CardType.c3;
				case 4:
					return CardType.c4;
				}
			}
			
			if(length==2&&Common.getColor(list.get(1))==5)
				return CardType.c2;
			
			if(length==4 &&((Common.getValue(list.get(0))==Common.getValue(list.get(length-2)))||
					Common.getValue(list.get(1))==Common.getValue(list.get(length-1))))
				return CardType.c31;
			else {
				return CardType.c0;
			}
		}
		
		if(length>=5)
		{
			Card_index card_index=new Card_index();
			for(int i=0;i<4;i++)
				card_index.a[i]=new ArrayList<Integer>();
			
			Common.getMax( card_index,list); 
			
			if(card_index.a[2].size()==1 &&card_index.a[1].size()==1 && length==5)
				return CardType.c32;
			
			if(card_index.a[3].size()==1 && length==6)
				return CardType.c411;
			if(card_index.a[3].size()==1 && card_index.a[1].size()==2 &&length==8)
				return CardType.c422;
			
			if((Common.getColor(list.get(0))!=5)&&(card_index.a[0].size()==length) &&
					(Common.getValue(list.get(0))-Common.getValue(list.get(length-1))==length-1))
				return CardType.c123;
			
			if(card_index.a[1].size()==length/2 && length%2==0 && length/2>=3
					&&(Common.getValue(list.get(0))-Common.getValue(list.get(length-1))==(length/2-1)))
				return CardType.c1122;
			
			if(card_index.a[2].size()==length/3 && (length%3==0) &&
					(Common.getValue(list.get(0))-Common.getValue(list.get(length-1))==(length/3-1)))
				return CardType.c111222;
			
			if(card_index.a[2].size()==length/4 &&
					((Integer)(card_index.a[2].get(length/4-1))-(Integer)(card_index.a[2].get(0))==length/4-1))
				return CardType.c11122234;
				
			
			if(card_index.a[2].size()==length/5 && card_index.a[2].size()==length/5 &&
					((Integer)(card_index.a[2].get(length/5-1))-(Integer)(card_index.a[2].get(0))==length/5-1))
				return CardType.c1112223344;
				
		}
		return CardType.c0;
	}
	
	
	public static void move(Card card,Point from,Point to){
		if(to.x!=from.x){
			double k=(1.0)*(to.y-from.y)/(to.x-from.x);
			double b=to.y-to.x*k;
			int flag=0;
			if(from.x<to.x)
				flag=20;
			else {
				flag=-20;
			}
			for(int i=from.x;Math.abs(i-to.x)>20;i+=flag)
			{
				double y=k*i+b;
			
				card.setLocation(i,(int)y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		card.setLocation(to);
	}
	
	public static void order(List<Card> list){
		Collections.sort(list,new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				
				int a1=Integer.parseInt(o1.name.substring(0, 1));
				int a2=Integer.parseInt(o2.name.substring(0,1));
				int b1=Integer.parseInt(o1.name.substring(2,o1.name.length()));
				int b2=Integer.parseInt(o2.name.substring(2,o2.name.length()));
				int flag=0;
				
				if(a1==5) b1+=100;
				if(a1==5&&b1==1) b1+=50;
				if(a2==5) b2+=100;
				if(a2==5&&b2==1) b2+=50;
				
				if(b1==1) b1+=20;
				if(b2==1) b2+=20;
				if(b1==2) b1+=30;
				if(b2==2) b2+=30;
				flag=b2-b1;
				if(flag==0)
					return a2-a1;
				else {
					return flag;
				}
			}
		});
	}
	
	public static void rePosition(Main m,List<Card> list,int flag){
		Point p=new Point();
		if(flag==0)
		{
			p.x=50;
			p.y=(450/2)-(list.size()+1)*15/2;
		}
		if(flag==1)
		{
			p.x=(800/2)-(list.size()+1)*21/2;
			p.y=450;
		}
		if(flag==2)
		{
			p.x=700;
			p.y=(450/2)-(list.size()+1)*15/2;
		}
		int length=list.size();
		for(int i=0;i<length;i++){
			Card card=list.get(i);
			Common.move(card, card.getLocation(), p);
			m.container.setComponentZOrder(card, 0);
			if(flag==1)p.x+=21;
			else p.y+=15;
			
		}
	}
	
	public static int getScore(List<Card> list){
		int count=0;
		for(int i=0,length=list.size();i<length;i++){
			Card card=list.get(i);
			if(card.name.substring(0, 1).equals("5"))
			{
				//System.out.println(card.name.substring(0, 1));
				count+=5;
			}
			if(card.name.substring(2, card.name.length()).equals("2"))
			{
				//System.out.println(2);
				count+=2;
			}
		}
		return count;
		
	}
	
	public static int getColor(Card card){
		return Integer.parseInt(card.name.substring(0,1));
	}
	
	public static int getValue(Card card){
		int i= Integer.parseInt(card.name.substring(2,card.name.length()));
		if(card.name.substring(2,card.name.length()).equals("2"))
			i+=13;
		if(card.name.substring(2,card.name.length()).equals("1"))
			i+=13;
		if(Common.getColor(card)==5)
			i+=2;
		return i;
	}
	
	public static void getMax(Card_index card_index,List<Card> list){
		int count[]=new int[14];
		for(int i=0;i<14;i++)
			count[i]=0;
		for(int i=0,length=list.size();i<length;i++){
			if(Common.getColor(list.get(i))==5)
				count[13]++;
			else
				count[Common.getValue(list.get(i))-1]++;
		}
		for(int i=0;i<14;i++)
		{
			switch (count[i]) {
			case 1:
				card_index.a[0].add(i+1);
				break;
			case 2:
				card_index.a[1].add(i+1);
				break;
			case 3:
				card_index.a[2].add(i+1);
				break;
			case 4:
				card_index.a[3].add(i+1);
				break;
			}
		}
	}
	
	public static Model getModel(List<Card> list){
		
		List list2=new ArrayList<Card>(list);
		Model model=new Model();
		
		Common.getBoomb(list2, model); 
		
		Common.getThree(list2, model);
		
		Common.getPlane(list2, model);
		
		Common.getTwo(list2, model);
		
		Common.getTwoTwo(list2, model);
		
		Common.get123(list2, model);
		
		Common.getSingle(list2, model);
		return model;
	}
	
	public static void get123(List<Card> list,Model model){
		List<Card> del=new ArrayList<Card>();
		if(list.size()>0&&(Common.getValue(list.get(0))<7 ||Common.getValue(list.get(list.size()-1))>10))
			return;
		if(list.size()<5)
			return;
		for(int i=0,length=list.size();i<length;i++)
		{
			int k=i;
			for(int j=i;j<length;j++){
				if(Common.getValue(list.get(i))-Common.getValue(list.get(j))==j-i)
				{
					k=j;
				}
			}
			if(k-i>=4)
			{
				String s="";
				for(int j=i;j<k;j++)
				{
					s+=list.get(j).name+",";
					del.add(list.get(j));
				}
				s+=list.get(k).name;
				del.add(list.get(k));
				model.a123.add(s);
				i=k;
			}
		}
		list.removeAll(del);
	}
	
	public static void getTwoTwo(List<Card> list,Model model){
		List<String> del=new ArrayList<String>();
		
		List<String> l=model.a2;
		if(l.size()<3)
			return ;
		Integer s[]=new Integer[l.size()];
		for(int i=0,length=l.size();i<length;i++){
			String []name=l.get(i).split(",");
			s[i]=Integer.parseInt(name[0].substring(2,name[0].length()));
		}
		//s0,1,2,3,4  13,9,8,7,6
		for(int i=0,length=l.size();i<length;i++){
			int k=i;
			for(int j=i;j<length;j++)
			{
				if(s[i]-s[j]==j-i)
					k=j;
			}
			if(k-i>=2)
			{
				String ss="";
				for(int j=i;j<k;j++)
				{
					ss+=l.get(j)+",";
					del.add(l.get(j));
				}
				ss+=l.get(k);
				model.a112233.add(ss);
				del.add(l.get(k));
				i=k;
			}
		}
		l.removeAll(del);
	}
	
	public static void getPlane(List<Card> list,Model model){
		List<String> del=new ArrayList<String>();//
		
		List<String> l=model.a3;
		if(l.size()<2)
			return ;
		Integer s[]=new Integer[l.size()];
		for(int i=0,length=l.size();i<length;i++){
			String []name=l.get(i).split(",");
			s[i]=Integer.parseInt(name[0].substring(2,name[0].length()));
		}
		for(int i=0,length=l.size();i<length;i++){
			int k=i;
			for(int j=i;j<length;j++)
			{
				if(s[i]-s[j]==j-i)
					k=j;
			}
			if(k!=i)
			{
				String ss="";
				for(int j=i;j<k;j++)
				{
					ss+=l.get(j)+",";
					del.add(l.get(j));
				}
				ss+=l.get(k);
				model.a111222.add(ss);
				del.add(l.get(k));
				i=k;
			}
		}
		l.removeAll(del);
	}
	
	public static void getBoomb(List<Card> list,Model model){
		List<Card> del=new ArrayList<Card>();
		
		if(list.size()>=2 &&Common.getColor(list.get(0))==5 && Common.getColor(list.get(1))==5)
		{
			model.a4.add(list.get(0).name+","+list.get(1).name); 
			del.add(list.get(0));
			del.add(list.get(1));
		}
		
		if(Common.getColor(list.get(0))==5&&Common.getColor(list.get(1))!=5)
		{
			del.add(list.get(0));
			model.a1.add(list.get(0).name);
		}
		list.removeAll(del);
		
		for(int i=0,length=list.size();i<length;i++){
			if(i+3<length && Common.getValue(list.get(i))==Common.getValue(list.get(i+3)))
			{
				String s=list.get(i).name+",";
				s+=list.get(i+1).name+",";
				s+=list.get(i+2).name+",";
				s+=list.get(i+3).name;
				model.a4.add(s);
				for(int j=i;j<=i+3;j++)
					del.add(list.get(j));
				i=i+3;
			}
		}
		list.removeAll(del);
	}
	
	public static void getThree(List<Card> list,Model model){
		List<Card> del=new ArrayList<Card>();
		
		for(int i=0,length=list.size();i<length;i++){
			if(i+2<length&&Common.getValue(list.get(i))==Common.getValue(list.get(i+2)))
			{
				String s=list.get(i).name+",";
				s+=list.get(i+1).name+",";
				s+=list.get(i+2).name;
				model.a3.add(s);
				for(int j=i;j<=i+2;j++)
					del.add(list.get(j));
				i=i+2;
			}
		}
		list.removeAll(del);
	}
	
	public static void getTwo(List<Card> list,Model model){
		List<Card> del=new ArrayList<Card>();
		
		for(int i=0,length=list.size();i<length;i++){
			if(i+1<length&&Common.getValue(list.get(i))==Common.getValue(list.get(i+1)))
			{
				String s=list.get(i).name+",";
				s+=list.get(i+1).name;
				model.a2.add(s);
				for(int j=i;j<=i+1;j++)
					del.add(list.get(j));
				i=i+1;
			}
		}
		list.removeAll(del);
	}
	
	public static void getSingle(List<Card> list,Model model){
		List<Card> del=new ArrayList<Card>();
		
		for(int i=0,length=list.size();i<length;i++){
				model.a1.add(list.get(i).name);
				del.add(list.get(i));
			}
		list.removeAll(del);
	}
	
	public static void hideCards(List<Card> list){
		for(int i=0,length=list.size();i<length;i++){
			list.get(i).setVisible(false);
		}
	}
	
	public static int checkCards(List<Card> c,List<Card>[] current){
		
		List<Card> currentlist=(current[0].size()>0)?current[0]:current[2];
		CardType cType=Common.jugdeType(c);
		
		if(cType!=CardType.c4&&c.size()!=currentlist.size())
			return 0;
		
		if(Common.jugdeType(c)!=Common.jugdeType(currentlist))
		{
			
			return 0;
		}
		
		
		if(cType==CardType.c4)
		{
			if(c.size()==2)
				return 1;
			if(currentlist.size()==2)
				return 0;
		}
		
		if(cType==CardType.c1||cType==CardType.c2||cType==CardType.c3||cType==CardType.c4){
			if(Common.getValue(c.get(0))<=Common.getValue(currentlist.get(0)))
			{
				 return 0;
			}else {
				return 1;
			}
		}
		
		if(cType==CardType.c123||cType==CardType.c1122||cType==CardType.c111222)
		{
			if(Common.getValue(c.get(0))<=Common.getValue(currentlist.get(0)))
				return 0;
			else 
				return 1;
		}
		
		if(cType==CardType.c31||cType==CardType.c32||cType==CardType.c411||cType==CardType.c422
				||cType==CardType.c11122234||cType==CardType.c1112223344){
			List<Card> a1=Common.getOrder2(c); 
			List<Card> a2=Common.getOrder2(currentlist);
			if(Common.getValue(a1.get(0))<Common.getValue(a2.get(0)))
				return 0;
		}
		return 1;
	}
	
	public static List getOrder2(List<Card> list){
		List<Card> list2=new ArrayList<Card>(list);
		List<Card> list3=new ArrayList<Card>();
		List<Integer> list4=new ArrayList<Integer>();
		int length=list2.size();
		int a[]=new int[20];
		for(int i=0;i<20;i++)
			a[i]=0;
		for(int i=0;i<length;i++)
		{
			a[Common.getValue(list2.get(i))]++;
		}
		int max=0;
		for(int i=0;i<20;i++){
			max=0;
			for(int j=19;j>=0;j--){
				if(a[j]>a[max])
					max=j;
			}

			for(int k=0;k<length;k++){
				if(Common.getValue(list2.get(k))==max){
					list3.add(list2.get(k));
				}
			}
			list2.remove(list3);
			a[max]=0;
		}
		return list3;
	}
}
class Card_index{
	List a[]=new ArrayList[4];
}
