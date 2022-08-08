package logic;

import engine.Logic;
import engine.gfx.Renderer;
import engine.gfx.model.RawTriangle;
import engine.gfx.shaders.FragmentShader;
import engine.gfx.shaders.VertexShader;
import math.Vector3f;

public class Logic_dummy extends Logic{
	
	private RawTriangle model;
	private VertexShader vs;
	private FragmentShader fs;

	private Vector3f p1;
	private Vector3f v1;
	private Vector3f p2;
	private Vector3f v2;


	@Override
	public void init() {
		this.model = new RawTriangle(new float[] {
				0,0,0,
				100, 100, 0.2f,
				100,-100,0
		});
		this.vs = new VertexShader();
		this.fs = new FragmentShader();

		this.p1 = new Vector3f((float)(this.windowWidth * Math.random()), (float)(this.windowHeight * Math.random()),0);
		this.p2 = new Vector3f((float)(this.windowWidth * Math.random()), (float)(this.windowHeight * Math.random()),0);
		this.v2 = new Vector3f(0,0,0);
		this.v1 = new Vector3f(0,0,0);
	}

	@Override
	public void drawFrame(Renderer renderer) {
		double r1 = Math.random();
		Vector3f a1 = new Vector3f((float)Math.sin(r1*Math.PI*2), (float)Math.cos(r1 * Math.PI*2), 0.0f);
		v1.add(a1);
		double r2 = Math.random();
		Vector3f a2 = new Vector3f((float)Math.sin(r2*Math.PI*2), (float)Math.cos(r2 * Math.PI*2), 0.0f);
		v2.add(a2);
		final float maxSpeed = 5;
		if(v1.mag() > maxSpeed){
			v1.setMag(maxSpeed);
		}
		if(v2.mag() > maxSpeed){
			v2.setMag(maxSpeed);
		}

		p2.add(v2);
		p1.add(v1);

		if(p1.getComp(0) >= this.windowWidth){
			p1.setComp(0, this.windowWidth-1);
			v1.setComp(0, -v1.getComp(0));
		}else if(p1.getComp(0) < 0){
			p1.setComp(0, 0);
			v1.setComp(0, -v1.getComp(0));
		}
		if(p1.getComp(1) >= this.windowHeight){
			p1.setComp(1, this.windowHeight-1);
			v1.setComp(1, -v1.getComp(1));
		}else if(p1.getComp(1) < 0){
			p1.setComp(1, 0);
			v1.setComp(1, -v1.getComp(1));
		}

		if(p2.getComp(0) >= this.windowWidth){
			p2.setComp(0, this.windowWidth-1);
			v2.setComp(0, -v2.getComp(0));
		}else if(p2.getComp(0) < 0){
			p2.setComp(0, 0);
			v2.setComp(0, -v2.getComp(0));
		}
		if(p2.getComp(1) >= this.windowHeight){
			p2.setComp(1, this.windowHeight-1);
			v2.setComp(1, -v2.getComp(1));
		}else if(p2.getComp(1) < 0){
			p2.setComp(1, 0);
			v2.setComp(1, -v2.getComp(1));
		}



		this.model = new RawTriangle(new float[] {
				0,0,0,
				p1.getComp(0),p1.getComp(1),0,
				p2.getComp(0),p2.getComp(1),0
		});

		renderer.renderTriangle(model, vs, fs);
	}

	@Override
	public void cleanUp() {
		
	}
	
	
}
