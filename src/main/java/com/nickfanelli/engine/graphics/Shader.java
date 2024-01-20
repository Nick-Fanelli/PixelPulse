package com.nickfanelli.engine.graphics;

import com.nickfanelli.engine.utils.EngineUtils;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final String shaderName;

    private int shaderProgramID;

    public Shader(String shaderName) {
        this.shaderName = shaderName;
    }

    public void compile() {

        String vertexFileContents   = EngineUtils.loadEngineResourceFileAsText(shaderName + ".vert.glsl");
        String fragmentFileContents = EngineUtils.loadEngineResourceFileAsText(shaderName + ".frag.glsl");

        int vertexID    = this.compileShader(vertexFileContents, GL_VERTEX_SHADER);
        int fragmentID  = this.compileShader(fragmentFileContents, GL_FRAGMENT_SHADER);

        this.shaderProgramID = glCreateProgram();

        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);

        glLinkProgram(shaderProgramID);

        int success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);

        if(success == GL_FALSE) {
            int length = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.err.println(glGetProgramInfoLog(shaderProgramID, length));
            throw new RuntimeException("Error linking shader program: " + shaderName);
        }

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    public void delete() {
        glDeleteProgram(this.shaderProgramID);
    }

    public static void unbind() {
        glUseProgram(0);
    }

    public void bind() {
        glUseProgram(this.shaderProgramID);
    }

    private int compileShader(String shaderSource, int shaderType) {

        int shaderID = glCreateShader(shaderType);;

        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);

        int success = glGetShaderi(shaderID, GL_COMPILE_STATUS);

        if(success == GL_FALSE) {
            int length = glGetShaderi(shaderID, GL_INFO_LOG_LENGTH);
            System.err.println(glGetShaderInfoLog(shaderID, length));
            throw new RuntimeException("Error creating shader of type: " + shaderType);
        }

        return shaderID;

    }

}
