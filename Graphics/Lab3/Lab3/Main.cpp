#include <gl/glew.h>
#include <gl/freeglut.h>
#include <iostream>

void init();
void mydisplay();
void reshape(int w, int h);

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);
	glutInitWindowSize(800, 800);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA | GLUT_DEPTH );
	glutCreateWindow("Shading");
	
	init();

	glutDisplayFunc(mydisplay);
	glutReshapeFunc(reshape);
	glutMainLoop();
	return 0;
}

void init(){
	GLfloat La[4] = { 0.1f, 0.1f, 0.1f, 1.0f }; // Ambient
	GLfloat Ld[4] = { 1.0f, 1.0f, 0.0f, 1.0f }; // Diffuse
	GLfloat Ls[4] = { 1.0f, 1.0f, 1.0f, 1.0f }; // Specular
	GLfloat Lp[4] = {}; // Position

	GLfloat ka[4] = { 0.5f, 0.5f, 0.5f, 1.0f }; // Ambient 50% 반사
	GLfloat kd[4] = { 0.9f, 0.9f, 0.9f, 0.9f }; // Diffuse 90% 반사
	GLfloat ks[4] = { 0.8f, 0.8f, 0.8f, 0.8f }; // Specular 80% 반사
	GLfloat shininess = 50.0f;

	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	glEnable(GL_DEPTH_TEST);

	// 광원에 대해서 Setting
	// Light Position을 안주면 Default로 가장 앞을 바라보게한다.
	glLightfv(GL_LIGHT0, GL_AMBIENT, ka);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, kd);
	glLightfv(GL_LIGHT0, GL_SPECULAR, ks);
		
	// 물질에 대해서 Setting
	// GL_FRONT_AND_BACK 앞뒤에 해당하는 물질을 따로 지정할수도 이렇게 같이 지정할 수도 있다.
	// glMaterialf = 값만 넣을 것 glMaterialfv 배열
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, La); 
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, Ld);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, Ls);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, shininess);// 알파 값 반짝거림의 효과 shininess 크기가 크면 클수록 퍼져 보이게 한다.
															// 1에 가까울수록 크다. 각도 8/10 에 승수 임으로 수가 작으면 크다.\

	
	glEnable(GL_LIGHTING); // 조명 효과를 '사용함'으로 설정한다.
	glEnable(GL_LIGHT0); // Light가 원래는 꺼져있는데 0번을 킨다.

}

void mydisplay(){
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt(	0, 0, 5, 
				0, 0, 0,
				0, 1, 0 );

	glutSolidTeapot(1.0f); // x_obj
	glFinish();
}

void reshape(int w, int h){
	
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60, (GLdouble) w/(GLdouble) h, 0.001f, 10000.0f);

	glViewport(0, 0, w, h);
}