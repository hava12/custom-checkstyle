package com.checkstyle.method;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ControllerMethodNameCheck extends AbstractCheck {
	@Override
	public int[] getDefaultTokens() {
		return new int[]{TokenTypes.METHOD_DEF};
	}

	@Override
	public int[] getAcceptableTokens() {
		return new int[0];
	}

	@Override
	public int[] getRequiredTokens() {
		return new int[0];
	}

	@Override
	public void visitToken(DetailAST ast) {
		DetailAST methodNameAST = ast.findFirstToken(TokenTypes.IDENT); // 메소드 이름 탐색
		String methodName = methodNameAST.getText();  // 메소드명 추출
		System.out.println("Method Name: " + methodName);

		log(ast.getLineNo(), "Custom check executed at text " + ast);
	}
}
