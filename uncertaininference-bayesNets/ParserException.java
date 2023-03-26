/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;

/**
 * Exceptions thrown during parsing.
 */
public class ParserException extends IOException {

	public static final long serialVersionUID = 1L;

	public ParserException(String msg) {
		super(msg);
	}

}

