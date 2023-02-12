# Patsaoglou Panteleimon, A.M. 5102

# IMPORTS
from random import randint
from sys import exit

# CHECKS IF A PLAYER HAS 0 COINS


def bankruptcy_checker():
    if 0 in playercoins:
        print("\nCurrent Balance:")
        for i in range(playernum):
            print("%s has %s coins" % (players[i], playercoins[i]))
        for i in range(len(playercoins)):
            if playercoins[i] == 0:
                input("%s is bankrupt. Game ends." % (players[i]))
                exit()
    else:
        return

# PICKS THE FIRST BANKER


def first_banker():
    banker = randint(1, playernum)-1
    print("%s is randomly chosen as banker" % (players[banker]), "\n")
    return banker

# PRINTS THE BALANCE OF EACH PLAYER


def balance():
    global b
    bankruptcy_checker()
    print("\nCurrent Balance:")
    for i in range(playernum):
        print("%s has %s coins" % (players[i], playercoins[i]))
    b = betting()
    banker_rolling(b)

# ASKS EACH PLAYER TO BET


def betting():

    bets = []
    for i in range(playernum):
        bets.append(0)

    # BANKER BET
    banker_bet = int(
        input("\n%s: You are the banker! Please enter a valid bet: " % (players[banker])))

    while not 1 <= banker_bet <= playercoins[banker]:
        banker_bet = int(
            input("%s: Please re-enter a valid bet: " % (players[banker])))

    bets[banker] = banker_bet
    playercoins[banker] -= banker_bet

    # PLAYER BET
    bound = banker_bet

    for i in range(playernum):
        if i == (banker):
            continue
        elif bound == 0:
            break
        else:
            players_bet = int(
                input("%s: Please enter a valid bet: " % (players[i])))

            while players_bet > playercoins[i] or bound-players_bet < 0:
                players_bet = int(
                    input("%s: Please re-enter a valid bet: " % (players[i])))
        bets[i] = players_bet
        bound -= players_bet
        playercoins[i] -= players_bet
    if bound != 0:
        playercoins[banker] += bound
        bets[banker] -= bound
    print("\nRound starts:")

    # PRINTS ALL THE BETS
    for i in range(playernum):
        if i == banker:
            print("%s: Banker with bank amount=%s" %
                  (players[banker], bets[banker]))
        else:
            print("%s: has bet:%s" % (players[i], bets[i]))
    print("\n")
    return bets

# ROLLING FOR THE BANKER


def banker_rolling(b):
    roll = []

    # banker roll
    input("Banker: press ENTER to roll dice")
    for i in range(3):
        roll.append(randint(1, 6))
    print("Banker rolled: %s" % (roll))
    banker_roll_checker(roll, b)

# CHECK BANKER'S ROLL


def banker_roll_checker(s, b):
    # INSTANT BANKER VICTORY
    x = 0
    # 4-5-6
    if (4 in s and 5 in s and 6 in s):
        print("Automatic Win! Banker wins all bets! Round ends!")
        playercoins[banker] = playercoins[banker]+sum(b)
        balance()
    # TRIPLE
    elif s[0] == s[1] == s[2]:
        print("Automatic Win! Banker wins all bets! Round ends!")
        playercoins[banker] = playercoins[banker]+sum(b)
        balance()
    # DOUBLE AND 6
    elif (s[0] == 6 and s[1] == s[2]) or (s[1] == 6 and s[0] == s[2]) or (s[2] == 6 and s[1] == s[0]):
        print("Automatic Win! Banker wins all bets! Round ends!")
        playercoins[banker] = playercoins[banker]+sum(b)
        balance()

    # INSTANT BANKER DEFEAT
    # 1-2-3
    elif (1 in s and 2 in s and 3 in s):
        print("Automatic Defeat! Banker loses all bets! Round ends!")
        next_banker()
    # DOUBLE AND 1
    elif (s[0] == 1 and s[1] == s[2]) or (s[1] == 1 and s[0] == s[2]) or (s[2] == 1 and s[1] == s[0]):
        print("Automatic Defeat! Banker loses all bets! Round ends!")
        next_banker()
    # SCORE IS INITIATED
    elif s[0] == s[1] and s[2] in [2, 3, 4, 5]:
        x = s[2]
        print("Banker scored %s points\n" % (x))
        player_rolling(x)
    elif s[0] == s[2] and s[1] in [2, 3, 4, 5]:
        x = s[1]
        print("Banker scored %s points\n" % (x))
        player_rolling(x)
    elif s[1] == s[2] and s[0] in [2, 3, 4, 5]:
        x = s[0]
        print("Banker scored %s points\n" % (x))
        player_rolling(x)
    # IF NOTHING FROM ABOVE BANKER RE-ROLLS
    else:
        print("Banker rolls again")
        banker_rolling(b)


# IF SCORE IS INITIATED PLAYERS MUST ROLL
def player_rolling(banker_score):
    global banker
    y = banker+1
    # REPEAT FOR ALL PLAYERS AROUND BANKER WITH RIGHT ROW
    while True:
        if y == playernum:
            y = 0
            if y == banker:
                for i in bets:
                    if i != bets[banker] and i != 0:
                        print("Banker wins round!")
                        balance()
                next_banker()
            else:
                input("%s: press ENTER to roll dice" % (players[y-1]))
                roll = []
                for i in range(3):
                    roll.append(randint(1, 6))
                print("Player rolled: %s" % (roll))
                player_roll_checker(banker_score, roll, y)
                y += 1

        elif y != playernum:
            if y == banker:
                for i in bets:
                    if i != bets[banker] and i != 0:
                        print("Banker wins round!")
                        balance()
                next_banker()
            else:
                input("%s: press ENTER to roll dice" % (players[y-1]))
                roll = []
                for i in range(3):
                    roll.append(randint(1, 6))
                print("Player rolled: %s" % (roll))
                player_roll_checker(banker_score, roll, y)
                y += 1

# CHECKING EACH PLAYER ROLL


def player_roll_checker(banker_score, roll, player):
    global banker
    global b
    global playercoins
    z = 0
    # INSTANT PLAYER WIN
    # 4-5-6
    if 4 in roll and 5 in roll and 6 in roll:
        print("%s wins!" %
              (players[player]))
        playercoins[player] = playercoins[player] + b[player]
        print("%s is the new banker" % (players[player]))
        banker = player
        balance()
    # TRIPLE
        print("%s wins!" %
              (players[player]))
        playercoins[player] = playercoins[player] + b[player]
        banker = player
        print("%s is the new banker" % (players[player]))
        banker = player
        balance()
    # PLAYER SCORES
    elif roll[0] == roll[1] and roll[2] in [2, 3, 4, 5]:
        z = roll[2]
        print("Player scored %s points\n" % (z))
        if z > banker_score:
            print("%s wins!" %
                  (players[player]))
            playercoins[player] = playercoins[player] + b[player]
            return
        elif z < banker_score:
            print("Banker wins!")
            playercoins[banker] = playercoins[banker] + b[player]
            return
        elif z == banker_score:
            print("It's a tie between the banker and the player!\n")
            input("%s: press ENTER to roll dice" % (players[player]))
            roll = []
            for i in range(3):
                roll.append(randint(1, 6))
            print("Player rolled: %s" % (roll))
            player_roll_checker(banker_score, roll, player)
    elif roll[0] == roll[2] and roll[1] in [2, 3, 4, 5]:
        z = roll[2]
        print("Player scored %s points\n" % (z))
        if z > banker_score:
            print("%s wins!" %
                  (players[player]))
            playercoins[player] = playercoins[player] + b[player]
            return
        elif z < banker_score:
            print("Banker wins!")
            playercoins[banker] = playercoins[banker] + b[player]
            return
        elif z == banker_score:
            print("It's a tie between the banker and the player!\n")
            input("%s: press ENTER to roll dice" % (players[player]))
            roll = []
            for i in range(3):
                roll.append(randint(1, 6))
            print("Player rolled: %s" % (roll))
            player_roll_checker(banker_score, roll, player)
    elif roll[2] == roll[1] and roll[0] in [2, 3, 4, 5]:
        z = roll[2]
        print("Player scored %s points\n" % (z))
        if z > banker_score:
            print("%s wins!" %
                  (players[player]))
            playercoins[player] = playercoins[player] + b[player]
            return
        elif z < banker_score:
            print("Banker wins!")
            playercoins[banker] = playercoins[banker] + b[player]
            return
        elif z == banker_score:
            print("It's a tie between the banker and the player!\n")
            input("%s: press ENTER to roll dice" % (players[player]))
            roll = []
            for i in range(3):
                roll.append(randint(1, 6))
            print("Player rolled: %s" % (roll))
            player_roll_checker(banker_score, roll, player)
    # 1-2-3
    elif (1 in roll and 2 in roll and 3 in roll):
        print("Banker wins!")
        playercoins[banker] = playercoins[banker] + b[player]
        return
    else:
        input("%s: press ENTER to roll dice" % (players[player]))
        roll = []
        for i in range(3):
            roll.append(randint(1, 6))
        print("Player rolled: %s" % (roll))
        player_roll_checker(banker_score, roll, player)

# NEXT BANKER IF AUTOMATIC BANKER DEFEAT


def next_banker():
    global banker
    if banker+1 == playernum:
        banker = 0
        balance()
    else:
        banker = banker+1
        balance()


# STARTING INPUTS
playernum = input("Number of players (between 2 and 6): ")

try:
    playernum = int(playernum)
except ValueError as e:
    print("Something wrong happened: %s" % (e))
    print("I'm setting the number of players to 3")
    playernum = 3

if not 2 <= playernum <= 6:
    print("I expected between 2 and 6 players")
    print("I'm setting the number of players to 3")
    playernum = 3

coinsnum = input("Number of coins per player (between 5 and 100): ")

try:
    coinsnum = int(coinsnum)

except ValueError as e:
    print("Something wrong happened: %s" % (e))
    print("I'm setting the number of coins to 10")
    coinsnum = 10

if not 5 <= coinsnum <= 100:
    print("I'm setting the number of coins to 10")
    coinsnum = 10

# MAIN PART
print("Game begins with %s players." % (playernum))
print("Each player has %s coins." % (coinsnum))

# STARTING PHASE

# IMPORTANT VARIABLES
players = []
playercoins = []
bets = []
s = []
b = []

for i in range(playernum):
    players.append("Player %s" % (i+1))
    playercoins.append(coinsnum)
    s.append(0)

banker = first_banker()
balance()
