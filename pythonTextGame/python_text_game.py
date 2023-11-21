# Python text game
# this is a game which generates random enemies which you then fight
import pygame
import random

pygame.init()

screen = pygame.display.set_mode((800, 600))
pygame.display.set_caption('Python Text Based Avdenture Game')

icon = pygame.image.load('PythonGames/RandomGamesPython/playerImages/player_base.png')
pygame.display.set_icon(icon)

# player icon
player_image = pygame.image.load('PythonGames/RandomGamesPython/playerImages/player_base.png')
player_x = 100
player_y = 200

def Player():
    global player_image
    screen.blit(player_image, (player_x, player_y))
Player()

# running = True
# while running:
#     # screen.fill((0, 0, 0))
#     for event in pygame.event.get():
#         if event.type == pygame.QUIT:
#             running = False
#             exit()
#         Player()
#         pygame.display.update()


#### SAVING/LOADING GAME ####################
# creates the variables for creating a new character
player_health_max = 100
player_mana_max = 100
player_defence_max = 1
player_health = player_health_max
player_mana = player_mana_max
player_defence = player_defence_max
player_attack = 1
player_exp = 0
times_leveled_up = 0
location = 'start'
number_of_wins = 0
player_level = 0
money = 0

multiplier = 1

# tells the user how they can save, or leave the game
print("Type 'exit' at any point to leave the game, this will also save progress")
print("Type 'save' at any point to save the game")

# creates the list of items from the player save data
def itemListMaker(items):
    player_items = []
    # removes these characters
    disallowed_characters = "[],'"
    splitted = []
    # splits the items by spaces (so don't put spaces in the item ids)
    splitted = items.split(" ")
    # for each individual word
    for each in splitted:
        # remove all of those characters
        for character in disallowed_characters:
            each = each.replace(character, "")
        # append this new string to the list
        player_items.append(each)
    # return the list to be applied to the dictionary
    return player_items

# loads the game for the user
def loadGame():
    global player_health_max
    global player_mana_max
    global player_defence_max
    global player_health
    global player_mana
    global player_defence
    global player_attack
    global number_of_wins
    global location
    global player_exp
    global player_level
    global times_leveled_up
    global money

    list = []
    save_game = open("playerStats" + player_save_name + ".txt","r")
    lines = save_game.readlines()
    count = 1
    # Strips the newline character
    for line in lines:
        count += 1
        if line == "":
            continue
        else:
            try:
                list.append(line)
            except AttributeError:
                print("Something went wrong")
    new_list = []
    counter = 0
    player_items = []
    # removes the suffix \n from each of the idexes, except for 12, not sure why 12 doesnt have the \n
    for each in list:
        counter += 1
        new_list.append(each[:-1])
        # if counter != 14:
        #     new_list.append(each[:-1])
        # else:
        #     new_list.append(each)
    # attaches the file results to the variables
    player_health_max = int(new_list[0])
    player_mana_max = int(new_list[1])
    player_defence_max = int(new_list[2])
    player_health = float(new_list[3])
    player_mana = float(new_list[4])
    player_defence = float(new_list[5])
    player_attack = float(new_list[6])
    number_of_wins = int(new_list[7])
    location = new_list[8]
    player_exp = float(new_list[9])
    player_level = int(new_list[10])
    times_leveled_up = int(new_list[11])
    money = float(new_list[12])
    player_items = itemListMaker(new_list[13])
    player_equipment = itemListMaker(new_list[14])
    save_game.close()
    global player

    # puts all this stuff into a dictionary
    player = dict({'health': player_health, 'mana': player_mana, 'defence': player_defence, 'attack': player_attack,
                'maxHealth': player_health_max, 'maxMana': player_mana_max, 'maxDefence': player_defence_max,
                'exp': player_exp, 'level': player_level, 'timesLeveled': times_leveled_up, 'player_items': player_items, 
                'player_money': money, 'player_equipment': player_equipment})

# if the player wants to leave, or save the game
def saveGame():
    global player
    global number_of_wins
    global location
    global player_exp
    global player_level
    global times_leveled_up
    save_game = open("playerStats" + player_save_name + ".txt","w+")
    save_game.writelines("{}\n".format(round(player['maxHealth'], 2)))
    save_game.writelines("{}\n".format(round(player['maxMana'], 2)))
    save_game.writelines("{}\n".format(round(player['maxDefence'], 2)))
    save_game.writelines("{}\n".format(round(player['health'], 2)))
    save_game.writelines("{}\n".format(round(player['mana'], 2)))
    save_game.writelines("{}\n".format(round(player['defence'], 2)))
    save_game.writelines("{}\n".format(round(player['attack'], 2)))
    save_game.writelines("{}\n".format(round(number_of_wins, 2)))
    save_game.writelines("{}\n".format(location))
    save_game.writelines("{}\n".format(round(player_exp, 2)))
    save_game.writelines("{}\n".format(round(player_level, 2)))
    save_game.writelines("{}\n".format(round(times_leveled_up, 2)))
    save_game.writelines("{}\n".format(round(player['player_money'], 0)))
    save_game.writelines("{}\n".format(player['player_items']))
    save_game.writelines("{}\n".format(player['player_equipment']))
    save_game.close()

# if the player wants a new game
def newGame():
    global player
    global number_of_wins
    global location
    global player_exp
    global player_level
    global times_leveled_up
    player_health = 100
    player_mana = 100
    player_defence = 1
    player_attack = 1
    player_health_max = 100
    player_mana_max = 100
    player_defence_max = 1
    player_exp = 0
    player_level = 1
    times_leveled_up = 0
    money = 0
    global player_equipment
    # variable name    ['helmet','chest', 'boots', 'weapon','bauble','bauble]
    player_equipment = ['empty', 'empty', 'empty', 'empty', 'empty', 'empty']
    player = dict({'health': player_health, 'mana': player_mana, 'defence': player_defence, 'attack': player_attack,
                    'maxHealth': player_health_max, 'maxMana': player_mana_max, 'maxDefence': player_defence_max,
                    'exp': player_exp, 'level': player_level, 'timesLeveled': times_leveled_up, 'player_items': [], 
                    'player_money': money, 'player_equipment': player_equipment})

def checkSaveOrLeave(input):
    if (input == 'save'):
        saveGame()
        return True
    if (input == 'exit'):
        saveGame()
        exit()

#### GAME STARTUP ##################
def userName():
    
    font = pygame.font.Font('freesansbold.ttf', 32)
 
    # create a text surface object,
    # on which text is drawn on it.
    text = font.render('GeeksForGeeks', True, 0, 0)
    
    # create a rectangular object for the
    # text surface object
    textRect = text.get_rect()
    
    X = 800
    Y = 600
    # set the center of the rectangular object.
    textRect.center = (X // 5, Y // 3)
    display_surface = pygame.display.set_mode((X, Y))

    white = (255, 255, 255)
    green = (0, 255, 0)
    blue = (0, 0, 128)
    black = (0, 0, 0)
    
    display_surface.fill((0, 0, 0))
    
    text = font.render('Type your username: ', True, white, black)
    display_surface.blit(text, textRect)
    pygame.display.update()
    input = []
    while True:
        for event in pygame.event.get():
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    print("WHY")
                    exit()
                if event.key == pygame.K_RETURN:
                    return input
                if event.key == pygame.K_q:
                    input.append('q')
                elif event.key == pygame.K_w:
                    input.append('w')
                elif event.key == pygame.K_e:
                    input.append("e")
                elif event.key == pygame.K_r:
                    input.append('r')
                elif event.key == pygame.K_t:
                    input.append("t")
                elif event.key == pygame.K_y:
                    input.append('y')
                elif event.key == pygame.K_u:
                    input.append("u")
                elif event.key == pygame.K_i:
                    input.append('i')
                elif event.key == pygame.K_o:
                    input.append("o")
                elif event.key == pygame.K_p:
                    input.append('p')
                elif event.key == pygame.K_a:
                    input.append("a")
                elif event.key == pygame.K_s:
                    input.append('s')
                elif event.key == pygame.K_d:
                    input.append("d")
                elif event.key == pygame.K_f:
                    input.append('f')
                elif event.key == pygame.K_g:
                    input.append("g")
                elif event.key == pygame.K_h:
                    input.append('h')
                elif event.key == pygame.K_j:
                    input.append("j")
                elif event.key == pygame.K_k:
                    input.append('k')
                elif event.key == pygame.K_l:
                    input.append("l")
                elif event.key == pygame.K_z:
                    input.append('z')
                elif event.key == pygame.K_x:
                    input.append("x")
                elif event.key == pygame.K_c:
                    input.append('c')
                elif event.key == pygame.K_v:
                    input.append('v')
                elif event.key == pygame.K_b:
                    input.append("b")
                elif event.key == pygame.K_n:
                    input.append('n')
                elif event.key == pygame.K_m:
                    input.append("m")
                elif event.key == pygame.K_BACKSPACE:
                    input.pop(-1)
            if event.type == pygame.QUIT:
                exit()
                
            text = font.render('Type your username: ', True, white, black)
            display_surface.blit(text, textRect)
            pygame.display.update()
            
            font2 = pygame.font.Font('freesansbold.ttf', 32)
            text2 = font2.render('Username: ', True, 0, 0)

            # create a rectangular object for the
            # text surface object
            textRect2 = text2.get_rect()

            # set the center of the rectangular object.
            textRect2 = (100, 400)
            display_surface2 = pygame.display.set_mode((X, Y))

            white = (255, 255, 255)
            green = (0, 255, 0)
            blue = (0, 0, 128)
            black = (0, 0, 0)
            
            display_surface2.fill((0, 0, 0))
            username = ''
            for each in input:
                username = username + each
            
            text2 = font2.render(username, True, white, black)
            display_surface2.blit(text2, textRect2)
            


# create a font object.
# 1st parameter is the font file
# which is present in pygame.
# 2nd parameter is size of the font
font = pygame.font.Font('freesansbold.ttf', 32)
 
# create a text surface object,
# on which text is drawn on it.
text = font.render('GeeksForGeeks', True, 0, 0)
 
# create a rectangular object for the
# text surface object
textRect = text.get_rect()
 
X = 800
Y = 600
# set the center of the rectangular object.
textRect.center = (X // 5, Y // 3)
display_surface = pygame.display.set_mode((X, Y))

white = (255, 255, 255)
green = (0, 255, 0)
blue = (0, 0, 128)
black = (0, 0, 0)

nothing = True
while nothing:
    display_surface.fill((0, 0, 0))
    
    text = font.render('Would you like to load or create a new game', True, white, black)
    display_surface.blit(text, textRect)

    textRectLoad = text.get_rect()
    text = font.render('Load', True, white, black)
    textRectLoad.center = ((X // 1.5) + 400, Y // 2)
    display_surface.blit(text, textRectLoad)
    
    textRectNew = text.get_rect()
    text = font.render('New', True, white, black)
    textRectNew.center = (X // 4.5, Y // 2)
    display_surface.blit(text, textRectNew)
    
    pygame.display.update()

    for event in pygame.event.get():
        mpos = pygame.mouse.get_pos()
        mpress = pygame.mouse.get_pressed()
        if mpos[0] <= 400 and mpress[0] == True:
            display_surface.fill((0, 0, 0))
            print("create")
            text = font.render('Creating game...', True, white, black)
            display_surface.blit(text, textRect)
            pygame.display.update()
            userName()
            newGame()
            nothing = False
            break
            
        elif mpos[0] >= 400 and mpress[0] == True:
            display_surface.fill((0, 0, 0))
            print("load")
            text = font.render('Loading game...', True, white, black)
            display_surface.blit(text, textRect)
            pygame.display.update()
            userName()
            loadGame()
            nothing = False
            break
        
        elif event.type == pygame.QUIT:
            running = False
            exit()

# determines whether it creates a new player file or loads an old one
load_or_save = input("Would you like to load or create a new game (load/new): ")
if (load_or_save == 'exit'):
    exit()

# gets the name of the account
player_save_name = input("What is your account name: ")
if (player_save_name == 'exit'):
    exit()

# if (load_or_save == "load"):
#     print("Loading Game...")
#     loadGame()
# elif (load_or_save == "new"):
#     print("Creating New Game...")
#     newGame()
# else:
#     print("Please type a valid input")


#### PLAYER AREA ########################
# levels up the stat that the use wants to level up
def levelUpstats():
    global player_attack
    global player
    global times_leveled_up
    stat_to_level = input("Which stat would you like to level up extra? health + 10 (h)/ mana + 10 (m)/ defence + 0.1 (d)/ attack + 0.1(a): ")
    saver = False
    saver = checkSaveOrLeave(stat_to_level)
    if (saver == True):
        levelUpstats()
    if (stat_to_level == 'h'):
        player['maxHealth'] += 20
        times_leveled_up += 1
    elif (stat_to_level == 'm'):
        player['maxMana'] += 20
        times_leveled_up += 1
    elif (stat_to_level == 'd'):
        player['defence'] += 0.2
        times_leveled_up += 1
    elif (stat_to_level == 'a'):
        player['attack'] += 0.2
        times_leveled_up += 1
    else:
        print("Not valid input")
        levelUpstats()
    # levels up everything once
    player['maxHealth'] += 10
    player['maxMana'] += 10
    player['defence'] += 0.1
    player['attack'] += 0.1
    # brings the users health and mana to max
    player['health'] = player['maxHealth']
    player['mana'] = player['maxMana']
    return times_leveled_up

# determines whether the user can level up or not
def levelUper(exp_gain):
    global player_exp
    global player_level
    global times_leveled_up
    player_exp += exp_gain
    # the level up requirement is the users level to the power of 3
    level_up_goal = player_level ** 3
    if (player_exp >= level_up_goal):
        player_level += 1
        if (player_level > times_leveled_up):
            print("You leveled Up!")
            times_leveled_up = levelUpstats()

# for equipment changes
def statUpdate(list, name, old_name):
    global player
    for each in list:
        if name == each['name']:
            player['health']   += each['health']
            player['maxHealth']+= each['health']
            player['mana']     += each['mana']
            player['maxMana']  += each['mana']
            player['defence']  += each['defence']
            player['attack']   += each['attack']
        if old_name == each['name']:
            player['health']   -= each['health']
            player['maxHealth']-= each['health']
            player['mana']     -= each['mana']
            player['maxMana']  -= each['mana']
            player['defence']  -= each['defence']
            player['attack']   -= each['attack']

#########################################
#### ITEMS AREA #########################
#########################################

# PLAYER EQUIPMENT
# formula for value --> health = 2, mana = 2, defence = 10, attack = 10
# helmets
list_of_helmets = []
def appenderH():
    list_of_helmets.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_helmets.append(dict({'name': 'slime_cap', 'health': 5, 'mana': 0, 'defence': 1, 'attack': 0, 'value': 20}))
    list_of_helmets.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_helmets.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_helmets.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
appenderH()
# chestplates
list_of_chestplates = []
def appenderC():
    list_of_chestplates.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_chestplates.append(dict({'name': 'slime_shirt', 'health': 10, 'mana': 0, 'defence': 2, 'attack': 0, 'value': 40}))
    list_of_chestplates.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_chestplates.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_chestplates.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
appenderC()
# boots
list_of_boots = []
def appenderB():
    list_of_boots.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_boots.append(dict({'name': 'slime_boots', 'health': 10, 'mana': 0, 'defence': 1, 'attack': 0, 'value': 30}))
    list_of_boots.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_boots.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_boots.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
appenderB()
# weapons
list_of_weapons = []
def appenderW():
    list_of_weapons.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_weapons.append(dict({'name': 'slime_stick', 'health': 0, 'mana': 10, 'defence': 0, 'attack': 1, 'value': 30}))
    list_of_weapons.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_weapons.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_weapons.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
appenderW()
# baubles
list_of_baubles = []
def appenderBaubles():
    list_of_baubles.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_baubles.append(dict({'name': 'slime_ring', 'health': 10, 'mana': 10, 'defence': 0, 'attack': 0, 'value': 40}))
    list_of_baubles.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_baubles.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
    list_of_baubles.append(dict({'name': 'placeHolder', 'health': 0, 'mana': 0, 'defence': 0, 'attack': 0, 'value': 0}))
appenderBaubles()

def changer(list, list_of, old, id):
    print("")
    equipment_to_equip = input("Which would you like to equip (type the name of the {} / cancel(c)): ".format(type))
    saver = False
    saver = checkSaveOrLeave(equipment_to_equip)
    if (saver == True):
        changer(list, list_of, old)
    elif (equipment_to_equip == 'c'):
        pass
    else:
        for each in list:
            if equipment_to_equip == each:
                player['player_equipment'][id] = each
                statUpdate(list_of, each, old)
                break

def change(type, list_of, id):
    print("Your current {} is {}".format(type, player['player_equipment'][id]))
    old_equip = player['player_equipment'][id]
    display_list = []
    for each in player['player_items']:
        for i in list_of:
            if each == i['name']:
                display_list.append(each)
    if (type == 'boots'):
        print("You have these {}: ".format(type), end='')
    else:
        print("You have these {}s: ".format(type), end='')
    num_of_things = 0
    for each in display_list:
        num_of_things += 1
        print(each, end=', ')
    if (num_of_things == 0):
        print("You have no {}s".format(type), end='')
        print("")
    else:
        changer(display_list, list_of, old_equip, id)


def changeEquipment():
    global list_of_helmets
    global list_of_chestplates
    global list_of_boots
    global list_of_weapons
    global list_of_baubles
    print("Which equipment do you want to change")
    which_equip = input("Helmet(1)  Chestplate(2)  Boots(3)  Weapon(4)  Bauble Slot 1(5)  Bauble Slot 2(6)  Cancel(0): ")
    saver = False
    saver = checkSaveOrLeave(which_equip)
    if (saver == True):
        changeEquipment()
    elif (which_equip == '1'):
        change('helmet', list_of_helmets, 0)
    elif (which_equip == '2'):
        change('chestplate', list_of_chestplates, 1)
    elif (which_equip == '3'):
        change('boots', list_of_boots, 2)
    elif (which_equip == '4'):
        change('weapon', list_of_weapons, 3)
    elif (which_equip == '5'):
        change('bauble', list_of_baubles, 4)
    elif (which_equip == '6'):
        change('bauble', list_of_baubles, 5)
    elif (which_equip == '0'):
        print("You cancelled the equipment swapping")
    else:
        changeEquipment()
def checkEquipment():
    global player
    equips = player['player_equipment']
    print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
    print("Helmet: {}  Chestplate: {}  Boots: {}  Weapon: {}  Bauble Slot 1: {}  Bauble Slot 2: {}"
          .format(equips[0], equips[1], equips[2], equips[3], equips[4], equips[5]))
    change_equipment = input("Would you like to change your equipment(y/n): ")
    saver = False
    saver = checkSaveOrLeave(change_equipment)
    if (saver == True):
        checkEquipment()
    elif change_equipment == 'y':
        changeEquipment()
    else:
        print("Change equipment cancelled")

# determines if the enemy drops an item or not
def itemDrop(enemy, enemy_level):
    global player
    item_drop_chance = random.randint(0, 10)

    if(item_drop_chance > 5):
        # makes it so that level 1 slimes will drop 1 potion instead of 2
        if (enemy_level <= 1):
            enemy_level = 2
        print("The {} dropped {} health potions (type 'health' to use them. They do not take up a turn)".format(enemy['name'], int(enemy_level / 2)))
        for number in range(0, int(enemy_level / 2)):
            what_potion = random.randint(0, 5)
            if (what_potion != 5):
                player['player_items'].append(enemy['potionDrop1'])
            else:
                player['player_items'].append(enemy['potionDrop2'])

    if(item_drop_chance == 1):
        drop = random.randint(0, 4)
        print("The {} dropped a rare drop! It dropped a(n) {}!".format(enemy['name'], enemy['rare_drop'][drop]))
        player['player_items'].append(enemy['rare_drop'][drop])
    else:
        for each in boss_list:
            if (enemy['name'] == boss_list):
                drop = random.randint(0, 4)
                print("The {} dropped it's rare drop! It dropped a(n) {}!".format(enemy['name'], enemy['rare_drop'][drop]))
                player['player_items'].append(enemy['rare_drop'][drop])

    player['player_money'] += (enemy['money'] * enemy_level)

#### ENEMIES AREA #######################

# Slimes
slime_rare = ['slime_cap', 'slime_shirt', 'slime_boots', 'slime_stick', 'slime_ring']
green_slime = dict({'name' : 'green slime', 'health': 5.0, 'mana': 0, 'defence': 1, 'attack': 1, 'expGive': 1, 'rare_drop': slime_rare, 'money': 1, 'potionDrop1': 'tinyHealthPotion', 'potionDrop2': 'tinyManaPotion'})
blue_slime = dict({'name' : 'blue slime', 'health': 10.0, 'mana': 0, 'defence': 1, 'attack': 2, 'expGive': 5, 'rare_drop': slime_rare, 'money': 5, 'potionDrop1': 'tinyHealthPotion', 'potionDrop2': 'tinyManaPotion'})
red_slime = dict({'name' : 'green slime', 'health': 20.0, 'mana': 0, 'defence': 2, 'attack': 3, 'expGive': 20, 'rare_drop': slime_rare, 'money': 20, 'potionDrop1': 'tinyHealthPotion', 'potionDrop2': 'tinyManaPotion'})
orange_slime = dict({'name' : 'orange slime', 'health': 2.0, 'mana': 0, 'defence': 5, 'attack': 4, 'expGive': 25, 'rare_drop': slime_rare, 'money': 25, 'potionDrop1': 'tinyHealthPotion', 'potionDrop2': 'tinyManaPotion'})
purple_slime = dict({'name' : 'purple slime', 'health': 10.0, 'mana': 10, 'defence': 1, 'attack': 0, 'expGive': 15, 'rare_drop': slime_rare, 'money': 15, 'potionDrop1': 'tinyHealthPotion', 'potionDrop2': 'tinyManaPotion'})
# boss
boss_slime = dict({'name' : 'boss slime', 'health': 10.0, 'mana': 0, 'defence': 1, 'attack': 5, 'expGive': 30, 'rare_drop': slime_rare, 'money': 15, 'potionDrop1': 'tinyHealthPotion', 'potionDrop2': 'tinyManaPotion'})

# boss list
boss_list = []
boss_list.append(boss_slime['name'])

#### LOCATIONS #########################
# location name = [bossLevel, enmy1, enmy2, enmy3... bossEnemy]
forest = [5, green_slime, blue_slime, orange_slime, boss_slime]
plains = [5, green_slime, blue_slime, orange_slime, boss_slime]


#### FIGHTING AREA #####################
# makes the bars
def barMaker(max, stat):
    dash_convert = (max/dashes)            # Get the number to divide by to convert health to dashes (being 10)
    current_dashes = int(stat/dash_convert)              # Convert health to dash count: 80/10 => 8 dashes
    remaining_stat = dashes - current_dashes       # Get the health remaining to fill as space => 12 spaces

    display = '-' * current_dashes                  # Convert 8 to 8 dashes as a string:   "--------"
    remaining_display = ' ' * remaining_stat             # Convert 12 to 12 spaces as a string: "            "
    results = []
    results.append(display)
    results.append(remaining_display)
    return(results)

dashes = 20  # Max Displayed dashes

# function from https://stackoverflow.com/questions/48035367/python-text-game-health-bar
# posted by Spencer Wieczorek
def do_stats(health, mana, max_health, max_mana, e_health, e_mana, enemy, e_level):
    
    ######### PLAYER BARS ######################
    
    returned = barMaker(max_health, health)
    health_display = returned[0]
    remaining_display = returned[1]
    
    returned = barMaker(max_mana, mana)
    mana_display = returned[0]
    remaining_display_mana = returned[1]
    
    returned = barMaker(enemy['health'] + e_level * enemy['health'] / 10, e_health)
    e_health_display = returned[0]
    e_remaining_display_health = returned[1]
    
    ######### ENEMY BARS ########################

    if enemy['mana'] == 0:
        print(end='')
    else:
        returned = barMaker(enemy['mana'], e_mana)
        e_mana_display = returned[0]
        e_remaining_display_mana = returned[1]  

    # player health and mana bars
    print("\n\n\n\nPlayer Stats                                                                                                              Enemy Stats")
    print("|" + health_display + remaining_display + "|", end='')  # Print out textbased healthbar
    print("|" + mana_display + remaining_display_mana + "|", end='')  # Print out textbased healthbar
    print("                                                                         ", end='')
    print("|" + e_health_display + e_remaining_display_health + "|")  # Print out textbased healthbar
    if enemy['mana'] == 0:
        print(end='')
    else:
        print("|" + e_mana_display + e_remaining_display_mana + "|")  # Print out textbased healthbar
    
    # player health and mana percentages
    print("    health " + str(health) + "/" + str(max_health), end='') 
    print("         mana " + str(mana) + "/" + str(max_mana), end='')
    print("        defence: {}    attack: {}".format(round(player['defence'], 1), round(player['attack'], 1)), end='')
    
    print("                                              health " + str(e_health) + '/' + str(enemy['health'] + e_level * enemy['health'] / 10), end='')
    print("        defence: {}    attack: {}".format(round(enemy['defence'] + (e_level) * enemy['defence'] / 10, 1), (round(enemy['attack'] + (e_level) * enemy['attack'] / 10, 1))))

def revive():
    global location
    print("You awake feeling slightly dazed. You notice that you have awoken back at the entrance to the forst and the plains.")
    location = 'start'
    explore()

def makeEnemies(number_of_wins, area):
    global multiplier
    if (number_of_wins != 0) and (number_of_wins % area[0] != 0):
        enemy_level = random.randint(1, 5)
        enemy_type = random.randint(1, len(area)-1)

        if (number_of_wins < area[0]):
            multiplier = 1
        else:
            multiplier = int(number_of_wins / area[0])

        if (enemy_type == 1):
            fight(area[1], enemy_level * multiplier)
        elif (enemy_type == 2):
            fight(area[2], enemy_level * multiplier)
        elif (enemy_type == 3):
            fight(area[3], enemy_level * multiplier)
        elif (enemy_type == 4):
            fight(area[4], enemy_level * multiplier)
        elif (enemy_type == 5):
            fight(area[5], enemy_level * multiplier)
        elif (enemy_type == 6):
            fight(area[6], enemy_level * multiplier)
        elif (enemy_type == 7):
            fight(area[7], enemy_level * multiplier)
    elif (number_of_wins % area[0] == 0 and number_of_wins != 0): # boss fight level
        print("boss fight")
        multiplier = int(number_of_wins / area[0])
        fight(area[-1], random.randint(number_of_wins-2, number_of_wins+5) * multiplier)
    else:
        print("first fight")
        fight(green_slime, 0)

def getAreaEnemies(location):
    if (location == 'forest'):
        makeEnemies(number_of_wins, forest)
    elif (location == 'plains'):
        makeEnemies(number_of_wins, plains)

loc_start = ['forest', 'plains']
loc_forest = ['forest', 'plains']
loc_plains = ['forest', 'plains']

def locDetermine(loc):
    global location
    global number_of_wins
    location = input("Where would you like to go? The {}(1)/The {}(2)/Change Equipment(0): ".format(loc[0], loc[1]))
    saver = False
    saver = checkSaveOrLeave(location)
    if (saver == True):
        locDetermine(loc)
    elif (location == "1"):
        print("\nYou walk into a thick forest. The light can barely make it through the dense canopy of leaves.")
        location = 'forest'
        getAreaEnemies('forest')
    elif (location == "2"):
        print("\nYou walk into a big open field, where you can see dozens of creatures roaming around.")
        location = 'plains'
        getAreaEnemies('plains')
    elif (location == "0"):
        checkEquipment()
        locDetermine(loc)
    else:
        print("please enter valid input")
        location = 'start'
        explore()

def explore():
    global number_of_wins
    global location
    if (location == 'start'):
        locDetermine(loc_start)
    if (location == 'forest'):
        locDetermine(loc_forest)
    if (location == 'plains'):
        locDetermine(loc_plains)

def fight(enemy, enemy_level): 
    global number_of_wins
    global player
    global player_level
    ran_away = False
    print("\n\n\n\n\n\n\n\n\nA {} appears before you! \nIt's level {}.".format(enemy['name'], enemy_level))
    enemy_health = enemy['health'] + enemy_level * enemy['health'] / 10
    enemy_health = round(enemy_health, 1)
    player_health_max = player['maxHealth']
    player_health = round(player['health'], 1)
    while (enemy_health > 0 and player_health > 0):
        # player stats get
        player_attack = player['attack']
        player_attack = round(player_attack, 1)
        player_mana = player['mana']
        player_mana = round(player_mana, 0)
        player_defence = player['defence']
        player_defence = round(player_defence, 1)
        # enemy stats get
        beginner = 0
        if (enemy_level == 0):
            beginner = 1
        enemy_attack = enemy['attack'] + (enemy_level + beginner) * enemy['attack'] / 20
        enemy_attack = round(enemy_attack, 1)
        enemy_mana = enemy['mana'] + (enemy_level + beginner) * enemy['mana'] / 20
        enemy_mana = round(enemy_mana, 0)
        enemy_defence = enemy['defence'] + (enemy_level + beginner) * enemy['defence'] / 100
        enemy_defence = round(enemy_defence, 1)
        do_stats(player_health, player_mana, player_health_max, player_mana_max, enemy_health, enemy_mana, enemy, enemy_level)
        # what does the player want to do 
        num_health_potions = 0
        num_mana_potions = 0
        for each in player['player_items']:
            if (each == 'tinyHealthPotion'):
                num_health_potions += 1
            if (each == 'tinyManaPotion'):
                num_mana_potions += 1
        print("\nYou have {} health potions and {} mana potions".format(num_health_potions, num_mana_potions))
        what_do = input("What do you want to do?   Attack(a)  /  Run(r)  /  Drink Health Potion(h)  /  Drink Mana Potion(m): ")
        not_used = checkSaveOrLeave(what_do)
        if (not_used == True):
            pass
        if (what_do == 'h'):
            counter = 0
            index = -1
            for each in player['player_items']:
                index += 1
                if each == 'tinyHealthPotion':
                    counter += 1
                    if(counter == 1):
                        player_health += 10
                        player['player_items'].pop(index)
            if (counter == 0):
                print("You have no health potions")
        elif (what_do == 'm'):
            counter = 0
            index = -1
            for each in player['player_items']:
                index += 1
                if each == 'tinyManaPotion':
                    counter += 1
                    if(counter == 1):
                        player_mana += 10
                        player['player_items'].pop(index)
            if (counter == 0):
                print("You have no mana potions")
        elif what_do == "a":
            # punch the enemy then the enemy fights back
            if (enemy_defence >= 2):
                print("\n\n\n\n\n\n\n\n\n\nYou hit the {} for {} damage".format(enemy['name'], round(player_attack/ (enemy_defence/2), 2)))
                enemy_health -= player_attack / int(enemy_defence/2)
                enemy_health = round(enemy_health, 1)
            else:
                print("\n\n\n\n\n\n\n\n\n\nYou hit the {} for {} damage".format(enemy['name'], round(player_attack), 1))
                enemy_health -= player_attack
                enemy_health = round(enemy_health, 1)
            if enemy_health > 0:
                # print("Enemy health: {}, Enemy Mana: {}".format(enemy_health, enemy_mana))
                enemy_attack = enemy_attack / player_defence
                enemy_attack = round(enemy_attack, 1)
                print("Enemy hits you. You take {} damage".format(round(enemy_attack, 1)))
                player_health -= enemy_attack
                player_health = round(player_health, 1)
        elif what_do == "r":
            print("You ran away")
            ran_away = True
            explore()
        else:
            print("\n\n\nPlease type valid input")
    if enemy_health <= 0:
        player['health'] = player_health
        print("You defeated the {}!".format(enemy['name']))
        number_of_wins += 1
        levelUper(enemy['expGive'] + int(enemy_level * enemy['expGive']/4))
        itemDrop(enemy, enemy_level)
        explore()
    
    elif (ran_away):
        player['health'] = player_health
        explore()
    elif (player_health <= 0):
        print("You died")
        player['health'] = player_health_max
        revive()


if __name__ == "__main__":
    location = 'start'
    explore()
