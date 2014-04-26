#include <gl/glew.h>
#include <gl/freeglut.h>
#include <iostream>

void init();
void InitGeometry();
void InitVBO();
void display();
void reshape(int w, int h);
void mouse(int button, int state, int x, int y);


typedef struct { 
	float location[3]; 
	float color[4]; 
} Vertex; 

typedef struct {
	GLubyte *order;
	int size;
} Index;

Index indeices[4];
Vertex verts[16]; // triangle vertices 
GLuint vboHandle[1]; // a VBO that contains interleaved positions and colors 
GLuint indexVBO[1]; 
GLdouble aspect = 3.0f/4.0f;
int frame_loop = 0;

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
	glutDisplayFunc(display);

	glutMainLoop();

	for(int i=0; i<sizeof(indeices);i++)
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
	float black = 64.0/255.0f;
	// Top left
	verts[0].location[0] = 2.8f; verts[0].location[1] = 3.8f; verts[0].location[2] = 0.0f; 
	verts[1].location[0] = 0.1f; verts[1].location[1] = 3.8f; verts[1].location[2] = 0.0f; 
	verts[2].location[0] = 0.1f; verts[2].location[1] = 0.1f; verts[2].location[2] = 0.0f;
	verts[3].location[0] = 2.8f; verts[3].location[1] = 0.1f; verts[3].location[2] = 0.0f;
	// Top right
	verts[4].location[0] = -0.1f; verts[4].location[1] = 3.8f; verts[4].location[2] = 0.0f;
	verts[5].location[0] = -2.8f; verts[5].location[1] = 3.8f; verts[5].location[2] = 0.0f;
	verts[6].location[0] = -2.8f; verts[6].location[1] = 0.1f; verts[6].location[2] = 0.0f;
	verts[7].location[0] = -0.1f; verts[7].location[1] = 0.1f; verts[7].location[2] = 0.0f;
	// Bottom right
	verts[8].location[0] = -0.1f; verts[8].location[1] = -0.1f; verts[8].location[2] = 0.0f; 
	verts[9].location[0] = -2.8f; verts[9].location[1] = -0.1f; verts[9].location[2] = 0.0f; 
	verts[10].location[0] = -2.8f; verts[10].location[1] = -3.8f; verts[10].location[2] = 0.0f; 
	verts[11].location[0] = -0.1f; verts[11].location[1] = -3.8f; verts[11].location[2] = 0.0f; 
	// Botoom Left
	verts[12].location[0] = 2.8f; verts[12].location[1] = -0.1f; verts[12].location[2] = 0.0f; 
	verts[13].location[0] = 0.1f; verts[13].location[1] = -0.1f; verts[13].location[2] = 0.0f; 
	verts[14].location[0] = 0.1f; verts[14].location[1] = -3.8f; verts[14].location[2] = 0.0f; 
	verts[15].location[0] = 2.8f; verts[15].location[1] = -3.8f; verts[15].location[2] = 0.0f; 

	// RGBA
	verts[0].color[0] = black; verts[0].color[1] = black; verts[0].color[2] = black; verts[0].color[3] = 1; 
	verts[1].color[0] = black; verts[1].color[1] = black; verts[1].color[2] = black; verts[1].color[3] = 1; 
	verts[2].color[0] = black; verts[2].color[1] = black; verts[2].color[2] = black; verts[2].color[3] = 1; 
	verts[3].color[0] = black; verts[3].color[1] = black; verts[3].color[2] = black; verts[3].color[3] = 1; 
	verts[4].color[0] = black; verts[4].color[1] = black; verts[4].color[2] = black; verts[4].color[3] = 1; 
	verts[5].color[0] = black; verts[5].color[1] = black; verts[5].color[2] = black; verts[5].color[3] = 1; 
	verts[6].color[0] = black; verts[6].color[1] = black; verts[6].color[2] = black; verts[6].color[3] = 1; 
	verts[7].color[0] = black; verts[7].color[1] = black; verts[7].color[2] = black; verts[7].color[3] = 1; 
	verts[8].color[0] = black; verts[8].color[1] = black; verts[8].color[2] = black; verts[8].color[3] = 1; 
	verts[9].color[0] = black; verts[9].color[1] = black; verts[9].color[2] = black; verts[9].color[3] = 1; 
	verts[10].color[0] = black; verts[10].color[1] = black; verts[10].color[2] = black; verts[10].color[3] = 1; 
	verts[11].color[0] = black; verts[11].color[1] = black; verts[11].color[2] = black; verts[11].color[3] = 1; 
	verts[12].color[0] = black; verts[12].color[1] = black; verts[12].color[2] = black; verts[12].color[3] = 1; 
	verts[13].color[0] = black; verts[13].color[1] = black; verts[13].color[2] = black; verts[13].color[3] = 1; 
	verts[14].color[0] = black; verts[14].color[1] = black; verts[14].color[2] = black; verts[14].color[3] = 1; 
	verts[15].color[0] = black; verts[15].color[1] = black; verts[15].color[2] = black; verts[15].color[3] = 1; 
	
	indeices[0].order = new GLubyte[18];
	indeices[0].size = 18;
	
	indeices[0].order[0] = 0; indeices[0].order[1] = 5; indeices[0].order[2] = 6;
	indeices[0].order[3] = 6; indeices[0].order[4] = 3; indeices[0].order[5] = 0;

	indeices[0].order[6] = 8; indeices[0].order[7] = 9; indeices[0].order[8] = 10;
	indeices[0].order[9] = 10; indeices[0].order[10] = 11; indeices[0].order[11] = 8;

	indeices[0].order[12] = 12; indeices[0].order[13] = 13; indeices[0].order[14] = 14;
	indeices[0].order[15] = 14; indeices[0].order[16] = 15; indeices[0].order[17] = 12;

	indeices[1].order = new GLubyte[12];
	indeices[1].size = 12;

	indeices[1].order[0] = 0; indeices[1].order[1] = 5; indeices[1].order[2] = 6;
	indeices[1].order[3] = 6; indeices[1].order[4] = 3; indeices[1].order[5] = 0;

	indeices[1].order[6] = 12; indeices[1].order[7] = 9; indeices[1].order[8] = 10;
	indeices[1].order[9] = 10; indeices[1].order[10] = 15; indeices[1].order[11] = 12;

	indeices[2].order = new GLubyte[18];
	indeices[2].size = 18;

	indeices[2].order[0] = 4; indeices[2].order[1] = 5; indeices[2].order[2] = 10;
	indeices[2].order[3] = 10; indeices[2].order[4] = 11; indeices[2].order[5] = 4;

	indeices[2].order[6] = 0; indeices[2].order[7] = 1; indeices[2].order[8] = 2;
	indeices[2].order[9] = 2; indeices[2].order[10] = 3; indeices[2].order[11] = 0;

	indeices[2].order[12] = 12; indeices[2].order[13] = 13; indeices[2].order[14] = 14;
	indeices[2].order[15] = 14; indeices[2].order[16] = 15; indeices[2].order[17] = 12;

	indeices[3].order = new GLubyte[12];
	indeices[3].size = 12;

	indeices[3].order[0] = 4; indeices[3].order[1] = 5; indeices[3].order[2] = 10;
	indeices[3].order[3] = 10; indeices[3].order[4] = 11; indeices[3].order[5] = 4;

	indeices[3].order[6] = 0; indeices[3].order[7] = 1; indeices[3].order[8] = 14;
	indeices[3].order[9] = 14; indeices[3].order[10] = 15; indeices[3].order[11] = 0;
}

void InitVBO() {
	glGenBuffers(1, vboHandle); // create two VBO handles, one position, one color handle 
	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[0]); // bind the first handle 
	glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex)*16, verts, GL_STATIC_DRAW); // allocate space and copy the 
	// position data over 
	glBindBuffer(GL_ARRAY_BUFFER, 0); // clean up 
	glGenBuffers(1, indexVBO); 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[0]); 
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*indeices[frame_loop].size, indeices[frame_loop].order, GL_STATIC_DRAW); // load the 
	// index data 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0); // clean up 
}

void display() { 
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT);

	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[0]); 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[0]); 
	
	glEnableClientState(GL_VERTEX_ARRAY); // enable the vertex array on the client side 
	glEnableClientState(GL_COLOR_ARRAY); // enable the color array on the client side 
	
	// number of coordinates per vertex (4 here), type of the coordinates, 
	// stride between consecutive vertices, and pointers to the first coordinate 
	glColorPointer(4, GL_FLOAT, sizeof(Vertex), (char*) NULL+ 12); 
	glVertexPointer(3,GL_FLOAT, sizeof(Vertex), (char*) NULL+ 0); 
	
	glDrawElements(GL_TRIANGLES, indeices[frame_loop].size, GL_UNSIGNED_BYTE, (char*) NULL+0); 
	
	glDisableClientState(GL_VERTEX_ARRAY); 
	glDisableClientState(GL_COLOR_ARRAY); 
	
	glFlush(); 
}

void reshape(int w, int h){
	glViewport(0, 0, w, h);
	glutPostRedisplay(); 
}

void mouse(int button, int state, int x, int y){
	if(button == GLUT_LEFT_BUTTON && state==GLUT_DOWN){
		frame_loop+=1;
		
		std::cout << frame_loop << std::endl;

		if(frame_loop == 4){
			frame_loop = 0;
		}
		glGenBuffers(1, vboHandle); // create two VBO handles, one position, one color handle 
		glBindBuffer(GL_ARRAY_BUFFER, vboHandle[0]); // bind the first handle 
		glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex)*16, verts, GL_STATIC_DRAW); // allocate space and copy the 
		// position data over 
		glBindBuffer(GL_ARRAY_BUFFER, 0); // clean up 
		glGenBuffers(1, indexVBO); 
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO[0]); 
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*indeices[frame_loop].size, indeices[frame_loop].order, GL_STATIC_DRAW); // load the 
		// index data 
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0); // clean up 
	}
}