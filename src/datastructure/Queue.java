package datastructure;

import model.BaseRateOffer;

public class Queue {
	
	private int maxSize;
	private BaseRateOffer[] driversQ;
	private int front;
	private int rear;
	private int nObjects;
	
	public Queue(int size){
		this.maxSize = size;
		this.driversQ = new BaseRateOffer[maxSize];
		front = 0;
		rear = -1;
		nObjects = 0;
	}
	
	public void insert(BaseRateOffer j){
		rear++;
		driversQ[rear] = j;
		nObjects ++;
	}
	
	public BaseRateOffer remove(){
		BaseRateOffer temp = driversQ[front];
		front++;
		if(front == maxSize){
			front =0;
		}
		nObjects--;
		return temp;
	}

	public boolean isEmpty(){
		return (nObjects == 0);
	}
	public void view(){
		System.out.println("[");
		for(int i =0; i < driversQ.length; i++){
		}
		System.out.println("]");
	}
	
	public int count(){
		return nObjects;
	}
}
