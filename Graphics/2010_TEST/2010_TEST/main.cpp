#include <gl/glew.h>
#include <gl/freeglut.h>
#include <iostream>
#include <vector>

#define TRUE 1
#define FALSE 0

using namespace std;

void InitGeometry();
void InitVBO();
void DrawVBO();
void display();
void reshape(int w, int h);
void mouse(int button, int state, int x, int y);
void mousewheel(int wheel, int direction, int x, int y);
void dragAndDrop(int x, int y);

typedef struct {
	GLubyte *order;
	int size;
} Index;

typedef struct { 
	float location[3]; 
	float color[4]; 
} Vertex; 

Vertex triangleVertex[56];
Vertex lineVertex[16]; 
Index indeices[5];
GLuint vboHandle[2];
GLuint indexVBO[5]; 

int frame_loop = 0;
GLdouble aspect = 3.0f/4.0f;
GLfloat eyex = 0.0f;
GLfloat eyez = 4.0f;

float opacity = 1.0f;
float scale = 1.0f;
int mouse_flag = FALSE;
int intrinsic_flag = TRUE;
int width = 300, height = 400;

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);
	glutInitWindowSize(width, height);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA | GLUT_DEPTH);
	glutCreateWindow("Frame");
	
	if( glewInit() != GLEW_OK ) {
		std::cout << "GLEW faild to init. :-(" << std::endl;
	}
	
	InitGeometry();
	InitVBO();

	glutReshapeFunc(reshape);
	glutMouseFunc(mouse);
	glutMouseWheelFunc(mousewheel);
	glutMotionFunc(dragAndDrop);
	glutDisplayFunc(display);
	glutMainLoop();

	for(int i=0; i<4;i++)
		delete indeices[i].order;
	return 0;
}

void InitGeometry() { 
	GLfloat black = 204.0f/255.0f;
	GLfloat white = 255.0f/255.0f;
	GLfloat gray = 50.0f/255.0f;

	Vertex vertex1[56] = {
							// Black
							2.8f, 3.8f, 0.0f,		black, black, black, opacity, // 0 Top Left 
							0.1f, 3.8f, 0.0f,		black, black, black, opacity, // 1
							0.1f, 0.1f, 0.0f,		black, black, black, opacity, // 2
							2.8f, 0.1f, 0.0f,		black, black, black, opacity, // 3

							-0.1f, 3.8f, 0.0f,		black, black, black, opacity, // 4 Top Right
							-2.8f, 3.8f, 0.0f,		black, black, black, opacity, // 5
							-2.8f, 0.1f, 0.0f,		black, black, black, opacity, // 6
							-0.1f, 0.1f, 0.0f,		black, black, black, opacity, // 7

							-0.1f, -0.1f, 0.0f,		black, black, black, opacity, // 8 Bottom Right
							-2.8f, -0.1f, 0.0f,		black, black, black, opacity, // 9
							-2.8f, -3.8f, 0.0f,		black, black, black, opacity, // 10
							-0.1f, -3.8f, 0.0f,		black, black, black, opacity, // 11

							2.8f, -0.1f, 0.0f,		black, black, black, opacity, // 12 Bottom Left
							0.1f, -0.1f, 0.0f,		black, black, black, opacity, // 13
							0.1f, -3.8f, 0.0f,		black, black, black, opacity, // 14
							2.8f, -3.8f, 0.0f,		black, black, black, opacity, // 15

							3.0f, 4.0f, 0.0f,		black, black, black, opacity, // 16 Background
							3.0f, -4.0f, 0.0f,		black, black, black, opacity, // 17
							-3.0f, -4.0f, 0.0f,		black, black, black, opacity, // 18
							-3.0f, 4.0f,	 0.0f,		black, black, black, opacity, // 19
							// White 
							2.8f, 3.8f, 0.0f,		white, white, white, opacity, // 20 Top Left 
							0.1f, 3.8f, 0.0f,		white, white, white, opacity, // 21
							0.1f, 0.1f, 0.0f,		white, white, white, opacity, // 22
							2.8f, 0.1f, 0.0f,		white, white, white, opacity, // 23

							-0.1f, 3.8f, 0.0f,		white, white, white, opacity, // 24 Top Right
							-2.8f, 3.8f, 0.0f,		white, white, white, opacity, // 25
							-2.8f, 0.1f, 0.0f,		white, white, white, opacity, // 26
							-0.1f, 0.1f, 0.0f,		white, white, white, opacity, // 27

							-0.1f, -0.1f, 0.0f,		white, white, white, opacity, // 28 Bottom Left
							-2.8f, -0.1f, 0.0f,		white, white, white, opacity, // 29
							-2.8f, -3.8f, 0.0f,		white, white, white, opacity, // 30
							-0.1f, -3.8f, 0.0f,		white, white, white, opacity, // 31

							2.8f, -0.1f, 0.0f,		white, white, white, opacity, // 32 Bottom Right
							0.1f, -0.1f, 0.0f,		white, white, white, opacity, // 33
							0.1f, -3.8f, 0.0f,		white, white, white, opacity, // 34
							2.8f, -3.8f, 0.0f,		white, white, white, opacity, // 35

							3.0f, 4.0f, 0.0f,		white, white, white, opacity, // 36 Background
							3.0f, -4.0f, 0.0f,		white, white, white, opacity, // 37
							-3.0f, -4.0f, 0.0f,		white, white, white, opacity, // 38
							-3.0f, 4.0f,	 0.0f,		white, white, white, opacity, // 39

							2.8f, 3.8f, -0.5f,		1.0f, black, black, opacity, // 40 Top Left 3D 
							0.1f, 3.8f, -0.5f,		1.0f, black, black, opacity, // 41
							0.1f, 0.1f, -0.5f,		1.0f, black, black, opacity, // 42
							2.8f, 0.1f,	-0.5f,		1.0f, black, black, opacity, // 43

							-0.1f, 3.8f, -0.5f,		1.0f, black, black, opacity, // 44 Top Right
							-2.8f, 3.8f, -0.5f,		1.0f, black, black, opacity, // 45
							-2.8f, 0.1f, -0.5f,		1.0f, black, black, opacity, // 46
							-0.1f, 0.1f, -0.5f,		1.0f, black, black, opacity, // 47

							-0.1f, -0.1f, -0.5f,		1.0f, black, black, opacity, // 48 Bottom Right
							-2.8f, -0.1f, -0.5f,		1.0f, black, black, opacity, // 49
							-2.8f, -3.8f, -0.5f,		1.0f, black, black, opacity, // 50
							-0.1f, -3.8f, -0.5f,		1.0f, black, black, opacity, // 51

							2.8f, -0.1f, -0.5f,		1.0f, black, black, opacity, // 52 Bottom Left
							0.1f, -0.1f, -0.5f,		1.0f, black, black, opacity, // 53
							0.1f, -3.8f,	 -0.5f,		1.0f, black, black, opacity, // 54
							2.8f, -3.8f, -0.5f,		1.0f, black, black, opacity
						};
	memcpy(triangleVertex, vertex1, 56*sizeof(Vertex));
	
	Vertex vertex2[16] = {
							2.8f, 3.8f, 0.0f,		gray, gray, gray, opacity, // 0 Top Left 
							0.1f, 3.8f, 0.0f,		gray, gray, gray, opacity, // 1
							0.1f, 0.1f, 0.0f,		gray, gray, gray, opacity, // 2
							2.8f, 0.1f, 0.0f,		gray, gray, gray, opacity, // 3

							-0.1f, 3.8f, 0.0f,		gray, gray, gray, opacity, // 4 Top Right
							-2.8f, 3.8f, 0.0f,		gray, gray, gray, opacity, // 5
							-2.8f, 0.1f, 0.0f,		gray, gray, gray, opacity, // 6
							-0.1f, 0.1f, 0.0f,		gray, gray, gray, opacity, // 7

							-0.1f, -0.1f, 0.0f,		gray, gray, gray, opacity, // 8 Bottom Right
							-2.8f, -0.1f, 0.0f,		gray, gray, gray, opacity, // 9
							-2.8f, -3.8f, 0.0f,		gray, gray, gray, opacity, // 10
							-0.1f, -3.8f, 0.0f,		gray, gray, gray, opacity, // 11

							2.8f, -0.1f, 0.0f,		gray, gray, gray, opacity, // 12 Bottom Left
							0.1f, -0.1f, 0.0f,		gray, gray, gray, opacity, // 13
							0.1f, -3.8f, 0.0f,		gray, gray, gray, opacity, // 14
							2.8f, -3.8f, 0.0f,		gray, gray, gray, opacity // 15
						};
	memcpy(lineVertex, vertex2, 16*sizeof(Vertex));

	GLubyte order[5][36] ={	 
							{
								0, 5, 6, 6, 3, 0, //Top
								8, 9, 10, 10,11, 8, // Bottom Left
								12, 13, 14, 14, 15, 12, // Bottom Right
								23, 26, 29, 29, 32, 23, // Background
								33, 28, 31, 31, 34, 33 // Background
							},
							{
								0, 5, 6, 6, 3, 0, // Top
								12, 9, 10, 10, 15, 12, // Bottom
								23, 26, 29, 29, 32, 23 // Background
							},
							{
								4, 5, 10, 10, 11, 4, // Left
								0, 1, 2, 2, 3, 0, // Right Top
								12, 13, 14, 14, 15, 12, // Right Bottom
								21, 24, 31, 31, 34, 21, // Background
								23, 22, 33, 33, 32, 23 // Background
							},
							{
								4, 5, 10, 10, 11, 4, // Left
								0, 1, 14, 14, 15, 0, // Right
								21, 24, 31, 31, 34, 21 // Background
							},
							{
								0, 5, 5, 10, 10, 15, 15, 0
							}
						};	

	indeices[0].order = new GLubyte[30];
	indeices[0].size = 30;
	memcpy(indeices[0].order, order[0], 30*sizeof(GLubyte));

	indeices[1].order = new GLubyte[18];
	indeices[1].size = 18;
	memcpy(indeices[1].order, order[1], 18*sizeof(GLubyte));
	
	indeices[2].order = new GLubyte[30];
	indeices[2].size = 30;
	memcpy(indeices[2].order, order[2], 30*sizeof(GLubyte));

	indeices[3].order = new GLubyte[18];
	indeices[3].size = 18;
	memcpy(indeices[3].order, order[3], 18*sizeof(GLubyte));

	indeices[4].order = new GLubyte[8];
	indeices[4].size = 8;
	memcpy(indeices[4].order, order[4], 8*sizeof(GLubyte));
}

void InitVBO() {
	// ~ VBO Initiate Vertex & Color
	glGenBuffers(2, vboHandle); 
	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[0]); 
	glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex)*56, triangleVertex, GL_STATIC_DRAW);
	glBindBuffer(GL_ARRAY_BUFFER, 0);

	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[1]); 
	glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex)*16, lineVertex, GL_STATIC_DRAW);
	glBindBuffer(GL_ARRAY_BUFFER, 0);

	// ~ VBO Initiate Index
	glGenBuffers(5, indexVBO); 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[0]); 
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*indeices[0].size, indeices[0].order, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[1]); 
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*indeices[1].size, indeices[1].order, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[2]); 
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*indeices[2].size, indeices[2].order, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[3]); 
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*indeices[3].size, indeices[3].order, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[4]); 
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*indeices[4].size, indeices[4].order, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
}

void DrawVBO(){

	// ~ Triangles
	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[0]); 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[frame_loop]); 
	
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_COLOR_ARRAY);
	
	glColorPointer(4, GL_FLOAT, sizeof(Vertex), (char*) NULL+ 12); 
	glVertexPointer(3, GL_FLOAT, sizeof(Vertex), (char*) NULL+ 0); 
	
	glDrawElements(GL_TRIANGLES, indeices[frame_loop].size, GL_UNSIGNED_BYTE, (char*) NULL+0); 
	
	glDisableClientState(GL_VERTEX_ARRAY); 
	glDisableClientState(GL_COLOR_ARRAY);

	glBindBuffer(GL_ARRAY_BUFFER,0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);

	// ~ Lines
	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[1]); 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[4]); 
		
	glEnableClientState(GL_VERTEX_ARRAY); 
	glEnableClientState(GL_COLOR_ARRAY);
		
	glColorPointer(4, GL_FLOAT, sizeof(Vertex), (char*) NULL+ 12); 
	glVertexPointer(3, GL_FLOAT, sizeof(Vertex), (char*) NULL+ 0); 

	glDrawElements(GL_LINES, indeices[4].size, GL_UNSIGNED_BYTE, (char*) NULL+0);
	glLineWidth(2.0f);

	glDisableClientState(GL_VERTEX_ARRAY); 
	glDisableClientState(GL_COLOR_ARRAY);
}

void display() { 

	glEnable(GL_DEPTH_TEST);

	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glViewport(0, 0, width, height);

	// ~ Intrinsic
	glMatrixMode(GL_PROJECTION); 
	glLoadIdentity(); 
	if(intrinsic_flag){
		glOrtho(-3, 3, -4, 4, 0.1, 100.0); 
	}else{
		gluPerspective(90.0, aspect, 0.1, 100.0);
	}

	glMatrixMode(GL_MODELVIEW); 
	glLoadIdentity(); 
	gluLookAt(	eyex, 0.0f, eyez,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);
	
	for(int i=0; i<4; i++){
		glPushMatrix();
		glTranslatef(0, 0, (GLfloat)-i*5);
		if(mouse_flag && i == 0){
			glScalef(1, scale, 1);
		}
		DrawVBO();
		glPopMatrix();

		frame_loop+=1;
		if(frame_loop == 4)
			frame_loop = 0;
	}
	
	glFlush(); 
}

void reshape(int w, int h){
	glViewport(0, 0, w, h);
	width = w;
	height = h;
	glutPostRedisplay(); 
}

void mouse(int button, int state, int x, int y){
	if(button == GLUT_LEFT_BUTTON && state==GLUT_DOWN){
		mouse_flag = TRUE;
	}else if(button == GLUT_LEFT_BUTTON && state == GLUT_UP){
		frame_loop+=1;
		if(frame_loop == 4){
			frame_loop = 0;
		}
		mouse_flag = FALSE;
		scale = 1.0f;
		glutPostRedisplay();
	}
}

void mousewheel(int wheel, int direction, int x, int y){
	
	if(direction < 0){
		eyex = eyex == 0 ? 0 : eyex-1;
		eyez = eyez == 4 ? 4 : eyez-1;
	}else if(direction > 0){
		eyex = eyex == 10 ? 10 : eyex+1;
		eyez = eyex > 4 && eyez == 10 ? 10 : eyez+1;
	}

	intrinsic_flag = FALSE;
	if(eyex == 0 && eyez == 4){
		intrinsic_flag = TRUE;
	}

	glutPostRedisplay();
}

void dragAndDrop(int x, int y){
	if(intrinsic_flag){
		if(mouse_flag){
			scale *= 0.9f;
			glutPostRedisplay();
		}
	}
}