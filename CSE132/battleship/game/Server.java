package battleship.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import battleship.client.util.Wrappers;

public class Server {
	final private int port;
	private int players;
	public DataOutputStream[] outs;
	public DataInputStream[] ins;
	public boolean[] ready;
	public Board[] boards;
	public HitResult[] hr;

	// int playerTurn, playerWait;

	// works fine with client 4

	public Server(int port) {
		this.port = port;
		players = 0;
		outs = new DataOutputStream[3];
		ins = new DataInputStream[3];
		hr = new HitResult[3];
		boards = new Board[3];
		ready = new boolean[]{false, false, false};

	}

	public void run() throws IOException {
		ServerSocket ss = new ServerSocket(port);
		while (true) {
			final Socket s = ss.accept();
			InputStream is = s.getInputStream();
			final DataInputStream dis = new DataInputStream(is);
			OutputStream os = s.getOutputStream();
			final DataOutputStream dos = new DataOutputStream(os);
			Thread t = new Thread() {

				public void run() {
					try {
						int playerId = 0;
						int playerOp = 0;
						String name = dis.readUTF();
						String pass = dis.readUTF();
						int state = dis.readByte();
						dos.writeUTF("Battleship Server");
						players++;
						playerId = players;
						if (playerId == 2) {
							playerOp = 1;
						} else {
							playerOp = 2;
						}
						final int playerTag = playerId;
						final int opponentTag = playerOp;
						dos.writeByte(playerId);
						outs[playerId] = dos;
						ins[playerId] = dis;
						dos.writeUTF("config");
						int numRows = 10;
						int numCols = 10;
						int[] shipLengths = new int[] { 1, 2, 3, 3 };
						boards[playerId] = new Board(numRows, numCols);
						int numShips = 4;
						dos.writeShort(numRows);
						dos.writeShort(numCols);
						dos.writeByte(numShips);
						for (int i = 0; i < shipLengths.length; i++) {
							dos.writeByte(shipLengths[i]);
						}
						for (int j = 0; j < shipLengths.length; j++) {
							int orientation = dis.readByte();
							int row = dis.readShort();
							int col = dis.readShort();
							if (orientation == 0) {
								boards[playerId].placeShip(Ship
										.genHorizontalShip(new Coordinate(row,
												col), shipLengths[j]));
							} else {
								boards[playerId].placeShip(Ship
										.genVerticalShip(new Coordinate(row,
												col), shipLengths[j]));
							}
						}
						hr[playerId] = new HitResult(boards[playerId].getPCS());
						dos.writeUTF("ok");
						ready[playerId] = true;
						Wrappers.notifyAll(Server.this);
						
						int playerTurn = 1;
						int playerWait = 2;
						int hold = -1;
						int moveRow = 0;
						int moveCol = 0;
						if (playerId == 1) {
							while(!ready[2]){
								Wrappers.wait(Server.this);
							}
							outs[playerTurn].writeUTF("PlayerTurn");
							outs[playerWait].writeUTF("PlayerTurn");
							outs[playerTurn].writeByte(playerTurn);
							outs[playerWait].writeByte(playerTurn);
							while (true) {
								String nextAction = ins[playerTurn].readUTF();
								if (nextAction.equals("message")) {
									String content = ins[playerTurn].readUTF();
									outs[1].writeUTF("broadcast");
									outs[2].writeUTF("broadcast");
									outs[1].writeUTF("Player " + playerTurn);
									outs[2].writeUTF("Player " + playerTurn);
									outs[1].writeUTF(content);
									outs[2].writeUTF(content);
								} else if (nextAction.equals("move")) {
									moveRow = ins[playerTurn].readShort();
									moveCol = ins[playerTurn].readShort();
									
									hr[playerWait].reset();
									boards[playerWait].processHit(moveRow, moveCol);

									String hit = hr[playerWait].isHit();
									//System.out.println(hit);
									String sink = hr[playerWait].isSunk();
									//System.out.println(sink);
									String win = hr[playerWait].isWin();
									//System.out.println(win);
									
									outs[playerTurn].writeUTF("PlayerFires");
									outs[playerWait].writeUTF("PlayerFires");
									
									outs[playerTurn].writeByte(playerTurn);
									outs[playerWait].writeByte(playerTurn);
									
									outs[playerTurn].writeShort(moveRow);
									outs[playerWait].writeShort(moveRow);
									
									outs[playerTurn].writeShort(moveCol);
									outs[playerWait].writeShort(moveCol);
									
									outs[playerTurn].writeUTF(hit);
									outs[playerWait].writeUTF(hit);
									
									outs[playerTurn].writeUTF(sink);
									outs[playerWait].writeUTF(sink);
									
									outs[playerTurn].writeUTF(win);
									outs[playerWait].writeUTF(win);
									
									hold = playerTurn;
									playerTurn = playerWait;
									playerWait = hold;
									outs[playerTurn].writeUTF("PlayerTurn");
									outs[playerWait].writeUTF("PlayerTurn");
									outs[playerTurn].writeByte(playerTurn);
									outs[playerWait].writeByte(playerTurn);
								}
								else {
									throw new Error("oops bad directive " + nextAction);
								}

							}
						}
					} catch (Throwable t) {
						throw new Error("saw error " +  t.getMessage());
					}
				}
			};
			t.start();
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server(3001);
		server.run();

	}
}
