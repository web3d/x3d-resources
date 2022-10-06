TARGET = make_temple
CC     = cc -n32
CFLAGS = -g

all: $(TARGET)

$(TARGET): $(TARGET).c
	$(CC) $(CFLAGS) $(TARGET).c -o $(TARGET)

make_pretty: make_pretty.c
	$(CC) $(CFLAGS) make_pretty.c -o make_pretty

run: $(TARGET)
	$(TARGET) > temple.wrl
	make_pretty > pretty.wrl

clean:
	rm -f core *~ $(TARGET)
