#include <gl/freeglut.h>
#include "cube.h"
#include <soil/SOIL.h>

void init();
void display();
void reshape(int w, int h);
void load_texture();

unsigned int tex_id;
// 4x4 checkboard image
GLubyte checkboard[]  = {
		0, 0, 0, 255,			0, 0, 0, 255,
		255, 255, 255, 255,		255,255,255,255,

		0, 0, 0, 255,			0, 0, 0, 255,
		255, 255, 255, 255,		255,255,255,255,

		255, 255, 255, 255,		255,255,255,255,
		0, 0, 0, 255,			0, 0, 0, 255,

		255, 255, 255, 255,		255,255,255,255,
		0, 0, 0, 255,			0, 0, 0, 255
};

int main(int argc, char* argv[]){
	glutInit(&argc, argv);
	glutInitWindowSize(800, 800);
	glutInitDisplayMode(GLUT_RGBA | GLUT_SINGLE | GLUT_DEPTH);
	glutCreateWindow("Texture Mapping Test");

	init();

	glutDisplayFunc(display);
	glutReshapeFunc(reshape);
	glutMainLoop();

	return 0;
}

void init(){
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

	load_texture();
}

void load_texture(){

	//File을 띄우서 Texture id를 이미지로 받아오고 만들어진 texture_id를 받아온다.
	tex_id = SOIL_load_OGL_texture("img_cheryl.jpg", SOIL_LOAD_AUTO, SOIL_CREATE_NEW_ID, SOIL_FLAG_INVERT_Y);

	//내부적으로해주게된다.
	//glGenTextures(1, &tex_id);

	glBindTexture(GL_TEXTURE_2D, tex_id);

	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

	//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

	//내부적으로 해주게된다.
	//glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 4, 4, 0, GL_RGBA, GL_UNSIGNED_BYTE, checkboard);
}

void display(){
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt(	3, 3, 3, 
				0, 0, 0,
				0, 1, 0);
	
	glEnable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_TEXTURE_2D);

	glBindTexture(GL_TEXTURE_2D, tex_id);

	// draw an object
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	
	glVertexPointer(3, GL_FLOAT, 0, vertices);
	glTexCoordPointer(2, GL_FLOAT, 0, texcoords);
	glDrawElements(GL_TRIANGLES, 12*3, GL_UNSIGNED_BYTE, indices);

	glDisableClientState(GL_VERTEX_ARRAY);
	glDisableClientState(GL_TEXTURE_COORD_ARRAY);

	glFlush();
}

void reshape(int w, int h){
	glViewport(0, 0, w, h);

	double aspect = (double) w / (double) h;

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(70.0, aspect, 0.0001, 10000.0);
}

