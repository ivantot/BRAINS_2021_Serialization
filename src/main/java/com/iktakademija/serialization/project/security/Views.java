package com.iktakademija.serialization.project.security;

public class Views {

	public static class Public {
	}

	public static class Private extends Public {
	}

	public static class CEO extends Private {

	}

	public static class Admin extends CEO {
	}
}
