#include <gl/glew.h>
#include <gl/freeglut.h>
#include <iostream>

void mydisplay();
void init();

float vertices[] = {
	// front
	1, 1, 1, -1, 1, 1, -1,-1, 1,
	-1,-1, 1, 1,-1, 1, 1, 1, 1,
	// back
	1,-1,-1, -1,-1,-1, -1, 1,-1,
	-1, 1,-1, 1, 1,-1, 1,-1,-1,
	// top
	1, 1, 1, 1, 1,-1, -1, 1,-1,
	-1, 1,-1, -1, 1, 1, 1, 1, 1,
	// bottom
	-1,-1,-1, 1,-1,-1, 1,-1, 1,
	1,-1, 1, -1,-1, 1, -1,-1,-1,
	// right
	1, 1, 1, 1,-1, 1, 1,-1,-1,
	1,-1,-1, 1, 1,-1, 1, 1, 1,
	// left
	-1, 1, 1, -1, 1,-1, -1,-1,-1,
	-1,-1,-1, -1,-1, 1, -1, 1, 1,
};

float colors[] = {
	// front, red
	1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
	1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
	// back, cyan
	0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1,
	0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1,
	// left, green
	0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
	0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
	// right, magenta
	1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1,
	1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1,
	// top, blue
	0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1,
	0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1,
	// bottom, yellow
	1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1,
	1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1,
};

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);
	glutInitWindowSize(600, 600);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA);
	glutCreateWindow("simple");
	
	if( glewInit() != GLEW_OK ) {
		std::cout << "GLEW faild to init. :-(" << std::endl;
	}
	
	init();

	glutDisplayFunc(mydisplay);
	glutMainLoop();
	return 0;
}

// Init 반드시 실행 해야함
void init()
{
	// Clear
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f); 
	glEnable(GL_DEPTH_TEST); // 가까이 있는 것을 표현하려고 하는것이다
	// Intrinsic
	glMatrixMode(GL_PROJECTION); 
	glLoadIdentity(); 
	glOrtho(-5.0, 5.0, 
			-5.0, 5.0, 
			-5.0, 5.0);
	// Extrinisic
	glMatrixMode(GL_MODELVIEW); 
	glLoadIdentity(); 
	gluLookAt(	2.0f, 2.0f, 2.0f,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);
}

void mydisplay()
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 

	glEnableClientState(GL_VERTEX_ARRAY); 
	glVertexPointer(3, GL_FLOAT, 0, vertices); 

	glEnableClientState(GL_COLOR_ARRAY);
	glColorPointer(4, GL_FLOAT, 0, colors);
	
	glDrawArrays(GL_TRIANGLES, 0, 36); 
	glDisableClientState(GL_VERTEX_ARRAY); 
	glDisableClientState(GL_COLOR_ARRAY); 
	
	glFlush();
}