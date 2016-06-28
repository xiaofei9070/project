package com.text.utils.ip;

import java.util.StringTokenizer;


public class IPTest {

	public static void main(String[] args) {
		IPSeeker seeker = IPSeeker.getInstance();
		String ip ="101.81.139.151";
		StringTokenizer st = new StringTokenizer(ip, ".");
		int count = st.countTokens();
		for(int i=0;i<count;i++){
			System.out.println(st.nextToken());
		}
        System.out.println(seeker.getAddress(ip));
	}
}
