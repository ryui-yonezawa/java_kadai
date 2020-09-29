package bean;

public class BoardBean {

	String no;
	String name;
	String mail;
	String text;
//	InputStream file;
//	byte[] bfile;
	String file;
	String insert_time;
	String updated_time;

	String Cfile;

	public BoardBean(String name, String mail, String text) {
		this.name = name;
		this.mail = mail;
		this.text = text;
	}

//	public BoardBean(String name, String mail, String text, InputStream file) {
//		this.name = name;
//		this.mail = mail;
//		this.text = text;
//		this.file = file;
//	}

	public BoardBean(String name, String mail, String text, String file) {
		this.name = name;
		this.mail = mail;
		this.text = text;
		this.file = file;
	}

//	public BoardBean(String no, String name, String mail, String text, InputStream file) {
//		this.no = no;
//		this.name = name;
//		this.mail = mail;
//		this.text = text;
//		this.file = file;
//	}

	public BoardBean(String no, String name, String mail, String text, String file, String Cfile) {
		this.no = no;
		this.name = name;
		this.mail = mail;
		this.text = text;
		this.file = file;
		this.Cfile = Cfile;
	}

//	public BoardBean(String no, String name, String mail, String text, byte[] bfile,
//			String insert_time, String updated_time) {
//		this.no = no;
//		this.name = name;
//		this.mail = mail;
//		this.text = text;
//		this.bfile = bfile;
//		this.insert_time = insert_time;
//		this.updated_time = updated_time;
//	}

	public BoardBean(String no, String name, String mail, String text, String file,
			String insert_time, String updated_time) {
		this.no = no;
		this.name = name;
		this.mail = mail;
		this.text = text;
		this.file = file;
		this.insert_time = insert_time;
		this.updated_time = updated_time;
	}




	public String getNo() {return no;}

	public void setNo(String no) {this.no = no;}


	public String getName() {return name;}

	public void setName(String name) {this.name = name;}


	public String getMail() {return mail;}

	public void setMail(String mail) {this.mail = mail;}


	public String getText() {return text;}

	public void setText(String text) {this.text = text;}


//	public InputStream getFile() {return file;}
//
//	public void setFile(InputStream file) {this.file = file;}
//
//
//	public byte[] getBFile() {return bfile;}
//
//	public void setBFile(byte[] file) {this.bfile = bfile;}


	public String getFile() {return file;}

	public void setFile(String file) {this.file = file;}


	public String getCFile() {return Cfile;}

	public void setCFile(String Cfile) {this.Cfile = Cfile;}


	public String getInsert_time() {return insert_time;}

	public void setInsert_time(String insert_time) {this.insert_time = insert_time;}


	public String getUpdated_time() {return updated_time;}

	public void setUpdated_time(String updated_time) {this.updated_time = updated_time;}


}
