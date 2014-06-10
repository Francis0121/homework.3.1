/*
*	Header File 추가시 반드시 glew 먼저 추가 해야되고
*	그 이유는 freeglut와 header파일 충돌이 있을수 있기 때문이다.
*/
#include <gl/glew.h>
#include <gl/freeglut.h>
#include <soil/SOIL.h>

#include <iostream>
#include <fstream>
#include <string>

void init();
void display();
void reshape(int, int);

std::string read_file(const std::string filename){
	std::ifstream file(filename);

	if(!file.is_open()){
		std::cerr << "Failed to read file :" << filename << std::endl;
		return "";
	}

	std::string content;
	std::string line = "";
	while(!file.eof()){
		std::getline(file, line);
		content.append(line + "\n");
	}
	file.close();

	return content;
}


GLuint vert_shader, frag_shader, program;
GLint mvp_matrix_loc, vertex_loc;//, color_loc;
GLint texcoord_loc, texid_loc;
GLint texid;

float vertices[18] = {
	-0.5f, 0.5f, 0.0f,
	-0.5f, -0.5f, 0.0f,
	0.5f, 0.5f, 0.0f,
	0.5f, 0.5f, 0.0f,
	-0.5f, -0.5f, 0.0f,
	0.5f, -0.5f, 0.0f
};

/*float colors[18] = {
	1.0f, 0.0f, 0.0f,
	0.0f, 1.0f, 0.0f,
	0.0f, 0.0f, 1.0f,
	0.0f, 0.0f, 1.0f,
	0.0f, 1.0f, 0.0f,
	1.0f, 1.0f, 0.0f
};*/

float texcoords[12] = {
	0.0f, 1.0f,
	0.0f, 0.0f,
	1.0f, 1.0f,
	1.0f, 1.0f,
	0.0f, 0.0f,
	1.0f, 0.0f
};

int main(int argc, char * argv[]){
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowSize(500, 500);
	glutCreateWindow("GLSL Programming 1");

	glutDisplayFunc(display);
	glutReshapeFunc(reshape);

	// 추가 되는 부분 glewInit 이 되는지 window에서 지원하지 않는 1.0이상 version을 쓰기 위해서.
	if(glewInit() != GLEW_OK){
		std::cout << "Faild to initialize glew " << std::endl;
		return 0;
	}

	init();

	glutMainLoop();
	return 0;
}

void display(void){
	glClearColor(1, 1, 1, 1);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glEnable( GL_DEPTH_TEST ); 

	float mvp_matrix[16] = {
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		0, 0, 0, 1
	};
	
	glUseProgram(program);

	glActiveTexture( GL_TEXTURE0 ); 
	glBindTexture( GL_TEXTURE_2D, texid );
	
	glUniformMatrix4fv(mvp_matrix_loc, 1, false, mvp_matrix);
	glUniform1i( texid_loc, 0 ); 

	glVertexAttribPointer(vertex_loc, 3, GL_FLOAT, false, 0, vertices);
	glEnableVertexAttribArray(vertex_loc);
	
	glVertexAttribPointer( texcoord_loc, 2, GL_FLOAT, false, 0, texcoords ); 
	glEnableVertexAttribArray( texcoord_loc ); 
	//glVertexAttribPointer(color_loc, 3, GL_FLOAT, false, 0, colors);
	//glEnableVertexAttribArray(color_loc);

	glDrawArrays(GL_TRIANGLES,0, 6);
	glUseProgram(0);
	glutSwapBuffers();
}

void reshape(int w, int h){
	glViewport(0, 0, w, h);
}

void init(){
	// file 내용을 불라와서 string에 저장시키고.
	std::string vert_src = read_file("simple.vert");
	std::string frag_src = read_file("simple.frag");

	const char* vert_src_ptr = vert_src.c_str();
	const char* frag_src_ptr = frag_src.c_str();

	// 해상 소스를 컴파일 시킨후
	vert_shader = glCreateShader(GL_VERTEX_SHADER);
	glShaderSource(vert_shader, 1, &vert_src_ptr, NULL);
	glCompileShader(vert_shader);

	frag_shader = glCreateShader(GL_FRAGMENT_SHADER);
	glShaderSource(frag_shader, 1, &frag_src_ptr, NULL);
	glCompileShader(frag_shader);

	// 프로그램을 만든다음에
	program = glCreateProgram();
	glAttachShader(program, vert_shader);
	glAttachShader(program, frag_shader);

	// Linking 시킨다.
	glLinkProgram(program);

	// Matrix에 있는 정보를 .. 포인터 객체에 연결시킨다.
	// get uniform, attribute location
	mvp_matrix_loc = glGetUniformLocation(program, "u_mvp_matrix");
	texid_loc = glGetUniformLocation( program, "u_texid" );
	vertex_loc = glGetAttribLocation(program, "a_vertex");
	//color_loc = glGetAttribLocation(program, "a_color");
	texcoord_loc = glGetAttribLocation( program, "a_texcoord" );

	texid = 
		SOIL_load_OGL_texture("cat.png", 4, 0, 
		SOIL_FLAG_POWER_OF_TWO | SOIL_FLAG_INVERT_Y );
}