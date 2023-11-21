import pygame

pygame.init()
width = 800
height = 800
screen = pygame.display.set_mode((width,height))

WHITE = (255,255,255)
G = 6.6743 * 10**-11
ZOOM = 20  # use numbers between 1 and 10 maybe 20

precision = 5
field = {}  # {(x, y): mass}
field[(width / 5, height/4)] = 6*10**22
field[(width / 2, height/2)] = 6*10**21
field[(width / 3, height/6)] = 6*10**21
field[(width / 5, 3 * height/4)] = 3*10**22
field[(3 * width / 4, height/2)] = 8*10**21
field[(4*width / 5, 5 * height / 6)] = 1*10**21

biggest = 0
for m in field.values():
    if m > biggest:
        biggest = m

MASS = biggest / (ZOOM * 10**15)  # largest mass / constant

for x in range(0, width, precision):
    for y in range(0, height, precision):
        g = 0
        for coords, mass in field.items():
            # if abs(x - coords[0]) > width/2 or abs(y - coords[1]) > height/2:
            #     continue
            distance = ((coords[0] - x)**2 + (coords[1] - y)**2)
            if distance == 0:
                pass
            else:
                g += (G*mass / distance) / MASS

        g = g % 765
        red = 0
        green = 0
        blue = 0
        if g > 255:
            red = 255
            if g > 510:
                green = 255
                blue = (g - 510) % 255
            else:
                green = g - 255
                blue = 0
        else:
            red = g
            green = 0
            blue = 0
        
        color = red, green, blue
        # print(f"changed color {x}, {y} : {color}")
        for i in range(precision):
            for j in range(precision):
                screen.set_at((x + i, y + j), color)
                pygame.display.flip()

running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

pygame.quit()