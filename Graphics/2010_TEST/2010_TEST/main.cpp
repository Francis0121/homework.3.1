#include <gl/glew.h>
#include <gl/freeglut.h>
#include <iostream>
#include <vector>

using namespace std;

void init();
void InitGeometry();
void InitVBO();
void DrawVBO();
void display();
void reshape(int w, int h);
void mouse(int button, int state, int x, int y);
void mousewheel(int wheel, int direction, int x, int y);

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

int main(int argc, char* argv[])
{
	glutInit(&argc, argv);
	glutInitWindowSize(300, 400);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA | GLUT_DEPTH);
	glutCreateWindow("Frame");
	
	if( glewInit() != GLEW_OK ) {
		std::cout << "GLEW faild to init. :-(" << std::endl;
	}
	
	init();
	InitGeometry();
	InitVBO();

	glutReshapeFunc(reshape);
	glutMouseFunc(mouse);
	glutMouseWheelFunc(mousewheel);
	glutDisplayFunc(display);
	glutMainLoop();

	for(int i=0; i<4;i++)
		delete indeices[i].order;
	return 0;
}

void init(){
	// ~ Intrinsic
	glMatrixMode(GL_PROJECTION); 
	glLoadIdentity(); 
	gluPerspective(90.0, aspect, 0.1, 100.0);

	// ~ Extrinisic
	glMatrixMode(GL_MODELVIEW); 
	glLoadIdentity(); 
	gluLookAt(	0.0f, 0.0f, 4.0f,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);
}

void InitGeometry() { 
	GLfloat black = 204.0f/255.0f;
	GLfloat white = 255.0f/255.0f;
	GLfloat gray = 50.0f/255.0f;

	Vertex vertex1[56] = {
							// Black
							2.8f, 3.8f, 0.0f,		black, black, black, 1, // 0 Top Left 
							0.1f, 3.8f, 0.0f,		black, black, black, 1, // 1
							0.1f, 0.1f, 0.0f,		black, black, black, 1, // 2
							2.8f, 0.1f, 0.0f,		black, black, black, 1, // 3

							-0.1f, 3.8f, 0.0f,		black, black, black, 1, // 4 Top Right
							-2.8f, 3.8f, 0.0f,		black, black, black, 1, // 5
							-2.8f, 0.1f, 0.0f,		black, black, black, 1, // 6
							-0.1f, 0.1f, 0.0f,		black, black, black, 1, // 7

							-0.1f, -0.1f, 0.0f,		black, black, black, 1, // 8 Bottom Right
							-2.8f, -0.1f, 0.0f,		black, black, black, 1, // 9
							-2.8f, -3.8f, 0.0f,		black, black, black, 1, // 10
							-0.1f, -3.8f, 0.0f,		black, black, black, 1, // 11

							2.8f, -0.1f, 0.0f,		black, black, black, 1, // 12 Bottom Left
							0.1f, -0.1f, 0.0f,		black, black, black, 1, // 13
							0.1f, -3.8f, 0.0f,		black, black, black, 1, // 14
							2.8f, -3.8f, 0.0f,		black, black, black, 1, // 15

							3.0f, 4.0f, 0.0f,		black, black, black, 1, // 16 Background
							3.0f, -4.0f, 0.0f,		black, black, black, 1, // 17
							-3.0f, -4.0f, 0.0f,		black, black, black, 1, // 18
							-3.0f, 4.0f,	 0.0f,		black, black, black, 1, // 19
							// White 
							2.8f, 3.8f, 0.0f,		white, white, white, 1, // 20 Top Left 
							0.1f, 3.8f, 0.0f,		white, white, white, 1, // 21
							0.1f, 0.1f, 0.0f,		white, white, white, 1, // 22
							2.8f, 0.1f, 0.0f,		white, white, white, 1, // 23

							-0.1f, 3.8f, 0.0f,		white, white, white, 1, // 24 Top Right
							-2.8f, 3.8f, 0.0f,		white, white, white, 1, // 25
							-2.8f, 0.1f, 0.0f,		white, white, white, 1, // 26
							-0.1f, 0.1f, 0.0f,		white, white, white, 1, // 27

							-0.1f, -0.1f, 0.0f,		white, white, white, 1, // 28 Bottom Left
							-2.8f, -0.1f, 0.0f,		white, white, white, 1, // 29
							-2.8f, -3.8f, 0.0f,		white, white, white, 1, // 30
							-0.1f, -3.8f, 0.0f,		white, white, white, 1, // 31

							2.8f, -0.1f, 0.0f,		white, white, white, 1, // 32 Bottom Right
							0.1f, -0.1f, 0.0f,		white, white, white, 1, // 33
							0.1f, -3.8f, 0.0f,		white, white, white, 1, // 34
							2.8f, -3.8f, 0.0f,		white, white, white, 1, // 35

							3.0f, 4.0f, 0.0f,		white, white, white, 1, // 36 Background
							3.0f, -4.0f, 0.0f,		white, white, white, 1, // 37
							-3.0f, -4.0f, 0.0f,		white, white, white, 1, // 38
							-3.0f, 4.0f,	 0.0f,		white, white, white, 1, // 39

							2.8f, 3.8f, -0.5f,		1.0f, black, black, 1, // 40 Top Left 3D 
							0.1f, 3.8f, -0.5f,		1.0f, black, black, 1, // 41
							0.1f, 0.1f, -0.5f,		1.0f, black, black, 1, // 42
							2.8f, 0.1f,	-0.5f,		1.0f, black, black, 1, // 43

							-0.1f, 3.8f, -0.5f,		1.0f, black, black, 1, // 44 Top Right
							-2.8f, 3.8f, -0.5f,		1.0f, black, black, 1, // 45
							-2.8f, 0.1f, -0.5f,		1.0f, black, black, 1, // 46
							-0.1f, 0.1f, -0.5f,		1.0f, black, black, 1, // 47

							-0.1f, -0.1f, -0.5f,		1.0f, black, black, 1, // 48 Bottom Right
							-2.8f, -0.1f, -0.5f,		1.0f, black, black, 1, // 49
							-2.8f, -3.8f, -0.5f,		1.0f, black, black, 1, // 50
							-0.1f, -3.8f, -0.5f,		1.0f, black, black, 1, // 51

							2.8f, -0.1f, -0.5f,		1.0f, black, black, 1, // 52 Bottom Left
							0.1f, -0.1f, -0.5f,		1.0f, black, black, 1, // 53
							0.1f, -3.8f,	 -0.5f,		1.0f, black, black, 1, // 54
							2.8f, -3.8f, -0.5f,		1.0f, black, black, 1
						};
	memcpy(triangleVertex, vertex1, 56*sizeof(Vertex));
	
	Vertex vertex2[16] = {
							2.8f, 3.8f, 0.0f,		gray, gray, gray, 1, // 0 Top Left 
							0.1f, 3.8f, 0.0f,		gray, gray, gray, 1, // 1
							0.1f, 0.1f, 0.0f,		gray, gray, gray, 1, // 2
							2.8f, 0.1f, 0.0f,		gray, gray, gray, 1, // 3

							-0.1f, 3.8f, 0.0f,		gray, gray, gray, 1, // 4 Top Right
							-2.8f, 3.8f, 0.0f,		gray, gray, gray, 1, // 5
							-2.8f, 0.1f, 0.0f,		gray, gray, gray, 1, // 6
							-0.1f, 0.1f, 0.0f,		gray, gray, gray, 1, // 7

							-0.1f, -0.1f, 0.0f,		gray, gray, gray, 1, // 8 Bottom Right
							-2.8f, -0.1f, 0.0f,		gray, gray, gray, 1, // 9
							-2.8f, -3.8f, 0.0f,		gray, gray, gray, 1, // 10
							-0.1f, -3.8f, 0.0f,		gray, gray, gray, 1, // 11

							2.8f, -0.1f, 0.0f,		gray, gray, gray, 1, // 12 Bottom Left
							0.1f, -0.1f, 0.0f,		gray, gray, gray, 1, // 13
							0.1f, -3.8f, 0.0f,		gray, gray, gray, 1, // 14
							2.8f, -3.8f, 0.0f,		gray, gray, gray, 1 // 15
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

	glMatrixMode(GL_MODELVIEW); 
	glLoadIdentity(); 
	gluLookAt(	eyex, 0.0f, eyez,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);

	for(int i=0; i<4; i++){
		
		glPushMatrix();
		glTranslatef(0, 0, (GLfloat)-i*5);
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
	glutPostRedisplay(); 
}

void mouse(int button, int state, int x, int y){
	if(button == GLUT_LEFT_BUTTON && state==GLUT_DOWN){
		frame_loop+=1;
		
		if(frame_loop == 4){
			frame_loop = 0;
		}
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
	
	glutPostRedisplay();
}