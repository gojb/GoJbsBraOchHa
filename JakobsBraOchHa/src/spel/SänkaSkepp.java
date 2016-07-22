package spel;
//Gr�na f�rsvinner inte n�r man r�r p� den. G�ra s� att man kan vrida p� skeppen. Kan bara l�gga sk�pp p� planen, och d�r det
//inte redan �r skepp. Fixa server och lista med spelande


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import GoJbFrame.GoJbFrame;

public class S�nkaSkepp {

	enum b�ttyp {fem,fyra,treEtt,treTv�,tv�};
	enum Riktning {hori,vert}

	Riktning riktning;

	GoJbFrame frame = new GoJbFrame(false);
	JLabel[] egnaLabels, annanLabels;
	JLabel egenLabel = new JLabel(), annanLabel = new JLabel(), egenText = new JLabel("Din ruta"),
			annanText = new JLabel("Motst�ndares ruta"), spelruta = new JLabel(), textruta = new JLabel(),
			inst�llningar = new JLabel() {
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < 22 * 5; i += 22) {
				g.setColor(new Color(200, 200, 200));
				if(bol5){
					g.fillRect(5 + i, 10, 20, 20);
				}
				if (i < 22 * 4&&bol4) {
					g.setColor(new Color(201, 200, 200));
					g.fillRect(5 + i, 60, 20, 20);
				}

				if (i < 22 * 3) {
					if(bol3){
						g.setColor(new Color(202, 200, 200));
						g.fillRect(5 + i, 110, 20, 20);
					}
					if(bol2){
						g.setColor(new Color(203, 200, 200));
						g.fillRect(5 + i, 160, 20, 20);
					}
				}
				if (i < 22 * 2&&bol1) {
					g.setColor(new Color(204, 200, 200));
					g.fillRect(5 + i, 210, 20, 20);
				}
			}

		};
	};

	Robot rb;

	boolean bol5 = true, bol4 = true, bol3 = true, bol2 = true, bol1 = true, stayInside, error;

	int last1, last2, antalRutor, y, y1,y3;

	double b, x, b1, x1,b3,x3;

	public static void main(String[] args) {
		new S�nkaSkepp();
	}

	public S�nkaSkepp() {
		frame.setSize(1000, 780);
		frame.setLocationRelativeTo(null);
		// frame.setResizable(false);
		// ---------------------------------------------------------------------------
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.blue);

		frame.add(textruta, BorderLayout.NORTH);
		textruta.setOpaque(true);
		frame.add(spelruta, BorderLayout.CENTER);
		spelruta.setOpaque(true);
		frame.add(inst�llningar, BorderLayout.SOUTH);
		inst�llningar.setOpaque(true);

		inst�llningar.setPreferredSize(new Dimension(1000, 250));

		textruta.setPreferredSize(new Dimension(1000, 50));
		textruta.setLayout(new GridLayout(1, 2));
		textruta.add(egenText);
		textruta.add(annanText);

		egenText.setHorizontalAlignment(SwingConstants.CENTER);
		egenText.setFont(new Font("", Font.BOLD, 27));
		annanText.setHorizontalAlignment(SwingConstants.CENTER);
		annanText.setFont(new Font("", Font.BOLD, 27));

		spelruta.setLayout(new GridLayout(1, 2, 0, 50));
		spelruta.setPreferredSize(new Dimension(1000, 500));
		spelruta.add(egenLabel);
		spelruta.add(annanLabel);
		spelruta.setBackground(new Color(122, 221, 232));

		egnaLabels = new JLabel[100];
		egenLabel.setLayout(new GridLayout(10, 10, 3, 3));

		annanLabels = new JLabel[100];
		annanLabel.setLayout(new GridLayout(10, 10, 3, 3));

		for (int i = 0; i < egnaLabels.length; i++) {
			egnaLabels[i] = new JLabel();
			annanLabels[i] = new JLabel();

			egnaLabels[i].setText(Integer.toString(i));
			egnaLabels[i].setFont(new Font("", 0, 0));

			annanLabels[i].setText(Integer.toString(i));
			annanLabels[i].setFont(new Font("", 0, 0));

			egnaLabels[i].setBackground(new Color(207, 217, 220));
			annanLabels[i].setBackground(new Color(145, 176, 223));

			egnaLabels[i].setOpaque(true);
			annanLabels[i].setOpaque(true);

			egenLabel.add(egnaLabels[i]);
			annanLabel.add(annanLabels[i]);

			// Mouselisteners
			egnaLabels[i].addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						if(riktning==Riktning.hori){
							riktning=Riktning.vert;
						}
						else{
							riktning=Riktning.hori;
						}
					}
					
					//					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());
					//					if(!error){
					//						for(int i = 0;i<antalRutor;i++){
					//							egnaLabels[clicked+(10*i)].setBackground(Color.green);
					//						}
					//					}
					
				}

				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub
					//					egnaLabels[last1].setBackground(new Color(207, 217, 220));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});
			egnaLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());

					for(int i = 1;i<antalRutor;i++){
						if(riktning==Riktning.vert){
							try {
								egnaLabels[clicked+(10*i)].setBackground(Color.black);
								error=false;
							} catch (Exception e2) {
								error=true;
								System.out.println("daads");
							}
							try {
								for(int i1 = 0;i1<egnaLabels.length;i1++){
									if(egnaLabels[i1].getBackground()==Color.black){
										if(antalRutor==5&&i1!=clicked&&i1!=clicked+(10)&&i1!=clicked+(10*2)&&i1!=clicked+(10*3)&&i1!=clicked+(10*4)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));
										}
										else if(antalRutor==4&&i1!=clicked&&i1!=clicked+(10)&&i1!=clicked+(10*2)&&i1!=clicked+(10*3)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
										else if(antalRutor==3&&i1!=clicked&&i1!=clicked+(10)&&i1!=clicked+(10*2)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
										else if(antalRutor==2&&i1!=clicked&&i1!=clicked+(10)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
									}
								}

							} catch (Exception e2) {
								// FIXME: handle exception
							}
							egnaLabels[clicked].setBackground(Color.black);
							last1 = clicked;
						}
						else{
							//Horisontellt

							try {
								egnaLabels[clicked+(1*i)].setBackground(Color.black);
								error=false;
							} catch (Exception e2) {
								error=true;
								System.out.println("daads");
							}
							try {
								for(int i1 = 0;i1<egnaLabels.length;i1++){
									
									b = ((double) (clicked+ 10)) / (double) (10);
									y = ((int) b); // y = radnummer, allts� 1 - 16
									x = ((((double) b) - ((double) y)) * 10) + 1d; // x = kolumnnummer, allts� 1 - 10
									if(egnaLabels[i1].getBackground()==Color.black){
										if(antalRutor==5&&i1!=clicked&&i1!=clicked+(1)&&i1!=clicked+(1*2)&&i1!=clicked+(1*3)&&i1!=clicked+(1*4)){

											b1 = ((double) (clicked + 4 + 10)) / (double) (10);
											y1 = Math.round(((int) b1)); // y = radnummer, allts� 1 - 16
											x1 = ((((double) b1) - ((double) y1)) * 10) + 1d; // x = kolumnnummer, allts� 1 - 10
											
											
											System.out.println(Math.round(x) + " --- "+Math.round(x1));
											egnaLabels[i1].setBackground(new Color(207, 217, 220));
											if(Math.round(x)>Math.round(x1)){
												System.out.println((Math.floor(b1-1)*10d) + " = x1");
												for(int i3=(int) Math.floor(((b1-1)*10d));i3<egnaLabels.length;i3++){
													System.err.println(i3);
													egnaLabels[i3].setBackground(new Color(207, 217, 220));
												}
											}
										}
										else if(antalRutor==4&&i1!=clicked&&i1!=clicked+(1)&&i1!=clicked+(1*2)&&i1!=clicked+(1*3)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
										else if(antalRutor==3&&i1!=clicked&&i1!=clicked+(1)&&i1!=clicked+(1*2)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
										else if(antalRutor==2&&i1!=clicked&&i1!=clicked+(1)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
									}
								}

							} catch (Exception e2) {
								// FIXME: handle exception
							}
							egnaLabels[clicked].setBackground(Color.black);
							last1 = clicked;
							
							
							
						}
					}









					//					System.out.println(egnaLabels[last1].getBackground()+" --- "+Color.green + " -- True? " + (egnaLabels[last1].getBackground()==Color.green));
					//					if(egnaLabels[last1+30].getBackground()!=Color.green){
					//						egnaLabels[last1].setBackground(new Color(207, 217, 220));
					//						try {
					//							for(int i = 1;i<7;i++){
					//								egnaLabels[last1+(10*i)].setBackground(new Color(207, 217, 220));
					//							}
					//						} catch (Exception e2) {
					//							// FIXME: handle exception
					//						}
					//					}
					//					egnaLabels[clicked].setBackground(Color.black);
					//					last1 = clicked;
					//
					//					

				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});

			annanLabels[i].addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub
					annanLabels[last2].setBackground(new Color(145, 176, 223));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub
					for(int i = 0;i<egnaLabels.length;i++){
						egnaLabels[i].setBackground((new Color(207, 217, 220)));
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});
			annanLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());
					annanLabels[last2].setBackground(new Color(145, 176, 223));
					annanLabels[clicked].setBackground(Color.black);
					last2 = clicked;

				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});
			spelruta.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});

		}
		frame.revalidate();
		inst�llningar.repaint();
		frame.repaint();
		inst�llningar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					rb = new Robot();
				} catch (AWTException e1) {
					// FIXME Auto-generated catch block
					e1.printStackTrace();
				}
				if(!stayInside){
					Color color = rb.getPixelColor(e.getLocationOnScreen().x, e.getLocationOnScreen().y);
					if (color.getRed() == 238) {
						color = rb.getPixelColor(e.getLocationOnScreen().x + 10, e.getLocationOnScreen().y);
					}
					System.out.println(color.getRed());
					if (color.getRed() == 200) {
						System.err.println(5);
						bol5=false;
						placeraSkepp(b�ttyp.fem);
					}
					else if (color.getRed() == 201) {
						System.err.println(4);
						bol4=false;
						placeraSkepp(b�ttyp.fyra);
					}
					else if (color.getRed() == 202) {
						System.err.println(3);
						bol3=false;
						placeraSkepp(b�ttyp.treEtt);
					}
					else if (color.getRed() == 203) {
						System.err.println(3);
						bol2=false;
						placeraSkepp(b�ttyp.treTv�);
					}
					else if (color.getRed() == 204) {
						System.err.println(2);
						bol1=false;
						placeraSkepp(b�ttyp.tv�);
					}
					inst�llningar.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {}
		});

	}


	public void placeraSkepp(b�ttyp b�t) {
		stayInside=true;
		riktning=Riktning.vert;
		if(b�t==b�ttyp.fem){
			antalRutor=5;
		}
		else if(b�t==b�ttyp.fyra){
			antalRutor=4;
		}
		else if(b�t==b�ttyp.treEtt){
			antalRutor=3;
		}
		else if(b�t==b�ttyp.treTv�){
			antalRutor=3;
		}
		else if(b�t==b�ttyp.tv�){
			antalRutor=2;
		}


	}
}
