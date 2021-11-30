package com.pincubics.main;

//import java.applet.Applet;
//import java.applet.AudioClip;
import java.io.*;
import javax.sound.sampled.*;


public class Sound {
	/* forma mais avancada de fazer som */
	public static class Clips{
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if(buffer == null) {
				return;
			}
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		public void play() { //para tocar apenas uma unica vez o audio
			if(clips == null) {
				return;
			}
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if(p >= count) {
				p = 0;
			}
		}
		
		public void loop() {
			if(clips == null) {
				return;
			}
			clips[p].loop(300);
		}
	}
	
	public static Clips music = load("/music.wav", 1);
	public static Clips hurtPlayer = load("/hurt.wav", 1);
	
	private static Clips load(String name, int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data, count);
		}
		catch(Exception e){
			try { // try e catch nao necessarios, mas bom utilizar para ter certeza que nao dara erro
				return new Clips(null, 0);
			}
			catch(Exception ee){
				return null;
			}
		}
	}
	/**/
	
	/* primeira forma e mais simples de fazer som */
	/*private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("/music.wav");
	public static final Sound hurtEffect = new Sound("/hurt.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}
		catch(Throwable e){
			
		}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}
		catch(Throwable e){}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}
		catch(Throwable e){}
	}
	*/
	/**/
	
}
