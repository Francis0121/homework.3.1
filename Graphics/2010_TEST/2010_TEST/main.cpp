#include <gl/glew.h>
#include <gl/freeglut.h>
#include <iostream>

void init();
void InitGeometry();
void InitVBO();
void display();

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
	glutDisplayFunc(display);

	glutMainLoop();
	return 0;
}

void init(){
	// Clear buffers to preset value
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	glEnable(GL_DEPTH_TEST);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	// ~ Intrinsic
	glMatrixMode(GL_PROJECTION); 
	glLoadIdentity(); 
	gluPerspective(90.0, 3.0/4.0, 0.1, 100.0);
		
	// ~ Extrinisic
	glMatrixMode(GL_MODELVIEW); 
	glLoadIdentity(); 
	gluLookAt(	0.0f, 0.0f, 4.0f,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);
}

typedef struct { 
	float location[3]; 
	float color[4]; 
} Vertex; 
	
Vertex verts[16]; // triangle vertices 
GLuint vboHandle[1]; // a VBO that contains interleaved positions and colors 
GLuint indexVBO; 

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

	// [0] = Opacity? [1,2,3] บฮลอ RGB
	verts[0].color[0] = 1; verts[0].color[1] = black; verts[0].color[2] = black; verts[0].color[3] = black; 
	verts[1].color[0] = 1; verts[1].color[1] = black; verts[1].color[2] = black; verts[1].color[3] = black; 
	verts[2].color[0] = 1; verts[2].color[1] = black; verts[2].color[2] = black; verts[2].color[3] = black; 
	verts[3].color[0] = 1; verts[3].color[1] = black; verts[3].color[2] = black; verts[3].color[3] = black; 
	verts[4].color[0] = 1; verts[4].color[1] = black; verts[4].color[2] = black; verts[4].color[3] = black; 
	verts[5].color[0] = 1; verts[5].color[1] = black; verts[5].color[2] = black; verts[5].color[3] = black; 
	verts[6].color[0] = 1; verts[6].color[1] = black; verts[6].color[2] = black; verts[6].color[3] = black; 
	verts[7].color[0] = 1; verts[7].color[1] = black; verts[7].color[2] = black; verts[7].color[3] = black; 
	verts[8].color[0] = 1; verts[8].color[1] = black; verts[8].color[2] = black; verts[8].color[3] = black; 
	verts[9].color[0] = 1; verts[9].color[1] = black; verts[9].color[2] = black; verts[9].color[3] = black; 
	verts[10].color[0] = 1; verts[10].color[1] = black; verts[10].color[2] = black; verts[10].color[3] = black; 
	verts[11].color[0] = 1; verts[11].color[1] = black; verts[11].color[2] = black; verts[11].color[3] = black; 
	verts[12].color[0] = 1; verts[12].color[1] = black; verts[12].color[2] = black; verts[12].color[3] = black; 
	verts[13].color[0] = 1; verts[13].color[1] = black; verts[13].color[2] = black; verts[13].color[3] = black; 
	verts[14].color[0] = 1; verts[14].color[1] = black; verts[14].color[2] = black; verts[14].color[3] = black; 
	verts[15].color[0] = 1; verts[15].color[1] = black; verts[15].color[2] = black; verts[15].color[3] = black; 
}

void InitVBO() { 
	GLubyte tindices[] = {	0, 5, 6, 6, 3, 0,
							8, 9, 10, 10, 11, 8,
							12, 13, 14, 14, 15, 12 }; // triangle vertex indices 

	glGenBuffers(1, vboHandle); // create two VBO handles, one position, one color handle 
	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[0]); // bind the first handle 
	glBufferData(GL_ARRAY_BUFFER, sizeof(Vertex)*16, verts, GL_STATIC_DRAW); // allocate space and copy the 
	// position data over 
	glBindBuffer(GL_ARRAY_BUFFER, 0); // clean up 
	glGenBuffers(1, &indexVBO); 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO); 
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GLubyte)*18, tindices, GL_STATIC_DRAW); // load the 
	// index data 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0); // clean up 
	// by now, we moved the position and color data over to the graphics card. There will be no redundant data 
	// copy at drawing time 
}

void display() 
{ 
	glBindBuffer(GL_ARRAY_BUFFER, vboHandle[0]); 
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVBO); 
	
	glEnableClientState(GL_VERTEX_ARRAY); // enable the vertex array on the client side 
	glEnableClientState(GL_COLOR_ARRAY); // enable the color array on the client side 
	
	// number of coordinates per vertex (4 here), type of the coordinates, 
	// stride between consecutive vertices, and pointers to the first coordinate 
	glColorPointer(4, GL_FLOAT, sizeof(Vertex), (char*) NULL+ 16); 
	glVertexPointer(3,GL_FLOAT, sizeof(Vertex), (char*) NULL+ 0); 
	
	glDrawElements(GL_TRIANGLES, 18, GL_UNSIGNED_BYTE, (char*) NULL+0); 
	
	glDisableClientState(GL_VERTEX_ARRAY); 
	glDisableClientState(GL_COLOR_ARRAY); 
	
	glFlush(); 
}