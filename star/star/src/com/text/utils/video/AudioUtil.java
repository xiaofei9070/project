package com.text.utils.video;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

public class AudioUtil {
	public static void main(String[] args) {
		change();
	}
	public static void change() 
	{
	File source = new File("D:\\msg_3810380917162b00147c0ae105.amr");
	File target = new File("D:\\2.mp3");
	AudioAttributes audio = new AudioAttributes();
	Encoder encoder = new Encoder();


	audio.setCodec("libmp3lame");
	EncodingAttributes attrs = new EncodingAttributes();
	attrs.setFormat("mp3");
	attrs.setAudioAttributes(audio);

	try {
		encoder.encode(source, target, attrs);
	} catch (IllegalArgumentException e) {
	e.printStackTrace();
	} catch (InputFormatException e) {
	e.printStackTrace();
	} catch (EncoderException e) {
	e.printStackTrace();
	}
	}
}
