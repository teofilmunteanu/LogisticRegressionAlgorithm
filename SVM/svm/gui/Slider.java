package svm.gui;

import java.awt.*;
import svm.SVM;

public class Slider extends Panel implements Runnable {
	About about;
	Thread t;
	Image im;
	Graphics img;
	int w1, h1;
	int delay = 50;
	String[] students;
	Font fnt = new Font("Arial", 0, 12);
	int y, w = 10;
	boolean init = true, control;
	int ww, hh;

	public Slider(About about, int ww, int hh) {
		this.about = about;
		this.ww = ww;
		this.hh = hh;
		setStudents();
		setFont(fnt);
		start();
	}

	public void setStudents() {
		String[] student = new String[40];
		student[0] = "ADOCHIEI ALIN";
		student[1] = "ALEXANDRU CRISTINA";
		student[2] = "AV\u0102D\u0102NEI ANDREI";
		student[3] = "BILIU\u021A\u0102 ELENA";
		student[4] = "BODOGAN FLORIN";
		student[5] = "BRAG\u0102 ELENA";
		student[6] = "C\u0102P\u0102\u021A\u00C2N\u0102 ANDREI";
		student[7] = "CIOC\u00eeRLAN VLAD-IOAN";
		student[8] = "CROITORIU DAN";
		student[9] = "D\u0102NIL\u0102 COSMIN";
		student[10] = "DIACONU MIRELA";
		student[11] = "DUNEA GEORGE";
		student[12] = "ENACHE-STRATULAT MARIUS";
		student[13] = "FELIU \u0218TEFAN";
		student[14] = "FLOREA ROBERT";
		student[15] = "FORMAGIU GEORGIANA";
		student[16] = "GHINIEI MARIUS";
		student[17] = "HORVAT ANTONIN";
		student[18] = "IGNAT MARIUS";
		student[19] = "LEONTE TUDOR";
		student[20] = "MALO\u0218 ALEXANDRU";
		student[21] = "MILICA ADINA";
		student[22] = "MUNTEANU TEOFIL";
		student[23] = "MURARA\u0218U MATEI";
		student[24] = "NICHIFOREL LAURA";
		student[25] = "ONIC\u0102 \u0218TEFAN";
		student[26] = "PANTILIMONESCU FLORINA";
		student[27] = "P\u0102UN MIRELA";
		student[28] = "PAV\u0102L R\u0102ZVAN";
		student[29] = "R\u0102DEANU LIVIU";
		student[30] = "ROTARU CLAUDIA";
		student[31] = "SCRIPCARU LUIS";
		student[32] = "TOFAN ANDREI";
		student[33] = "TURCU CONSTANTIN";
		student[34] = "\u021AUCHEL \u0218TEFAN";
		student[35] = "URECHE C\u0102T\u0102LINA";
		student[36] = "VARVAROI CRISTIAN";
		student[37] = "VASILACHE COSMIN";
		student[38] = "ZAMFIRA ANDREI";
		student[39] = "";

		students = new String[student.length * 100];
		for (int i = 0; i < students.length; i++)
			students[i] = student[i % student.length];
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void stop() {
		if (t != null) {
			t.stop();
			t = null;
		}
	}

	public void run() {
		do {
			repaint();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				return;
			}
		} while (true);
	}

	public void reset() {
		y = hh + 10;
		repaint();
		stop();
	}

	public final void paint(Graphics g) {
		if (init) {
			im = createImage(ww, hh);
			img = im.getGraphics();
			for (int i = 0; i < students.length; i++) {
				FontMetrics fm = img.getFontMetrics(fnt);
				h1 += fm.getHeight();
				if (fm.stringWidth(students[i]) > w1)
					w1 = fm.stringWidth(students[i]);
			}
			y = hh + 10;
			init = false;
		}
		Color color = null;
		for (int l = 0; l < students.length; l++) {
			float f = (float) hh / 4.0F;
			float f1 = 1.0F, f2 = 1.0F, f3 = 1.0F;
			int i1 = y + (int) (1.5 * getFont().getSize() * l);
			if (i1 >= 0 && i1 <= hh) {
				float ff = 0;
				if ((float) i1 <= f)
					ff = (float) i1 / f;
				else if ((float) i1 >= (float) hh - f)
					ff = ((float) hh - (float) i1) / f;
				else
					ff = 1.0F;
				color = new Color((int) ((float) 255 * ff), (int) ((float) 255 * ff), (int) ((float) 255 * ff));
			} else
				color = new Color(0, 0, 0);
			img.setColor(color);
			img.setFont(fnt);
			img.drawString(students[l], w, i1);
		}
		g.drawImage(im, 0, 0, this);
	}

	public final void update(Graphics g) {
		if (img != null) {
			img.setColor(Color.black);
			img.fillRect(0, 0, ww, hh);
		}
		if (y < -(int) ((float) h1 * 0.75f))
			y = hh + 10;
		else
			y--;
		paint(g);
	}

}
