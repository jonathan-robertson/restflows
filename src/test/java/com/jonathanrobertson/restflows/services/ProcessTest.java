package com.jonathanrobertson.restflows.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

public class ProcessTest {

	@Test
	public void testProcess() throws IOException, InterruptedException {
		ProcessBuilder ps = new ProcessBuilder("git", "--version");

		// From the DOC: Initially, this property is false, meaning that the
		// standard output and error output of a subprocess are sent to two
		// separate streams
		ps.redirectErrorStream(true);

		Process pr = ps.start();

		try (BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			pr.waitFor();
			System.out.println("ok!");
		}

		System.exit(0);
	}

	@Test
	public void testIsGitPresent() {
		System.out.println(isGitPresent());
	}

	private final ProcessBuilder gitVersionProcess = new ProcessBuilder("git", "--version").redirectErrorStream(true);

	private boolean isGitPresent() {
		try {
			Process p = gitVersionProcess.start();
			p.waitFor();
			return p.exitValue() == 0;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	@Test
	public void testGitShortStatus() throws IOException, InterruptedException {
		System.out.println(gitShortStatus());
	}

	private final ProcessBuilder gitShortStatus = new ProcessBuilder("git", "status", "-s", "-uno").redirectErrorStream(true);

	private String gitShortStatus() throws IOException, InterruptedException {
		Process p = gitShortStatus.start();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
			String line;
			StringJoiner joiner = new StringJoiner("\n");
			while ((line = in.readLine()) != null) {
				joiner.add(line);
			}
			p.waitFor();
			return joiner.toString();
		}
	}
}
