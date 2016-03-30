package io;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.lang.Byte;
import java.net.URL;


class Stream
{

    private static Stream instance;

    private Stream()
    {}

    public static Stream getInstance()
    {
	if (!(instance instanceof Stream))
	    instance = new Stream();

	return instance;
    }
    
    public void write(byte []bytes, String filename) throws IOException
    {
	FileOutputStream stream = new FileOutputStream(filename);
	stream.write(bytes);
	stream.close();
    }
	
    public void writeFile(String filename, String outname) throws IOException
    {
	this.write(this.getFileBytes(filename), outname);
    }

    public void writeURL(String url, String outname) throws IOException
    {
        this.write(getURLBytes(url), outname);
    }

    public byte [] getInputStreamBytes(InputStream stream) throws IOException
    {
	byte [] bytes;
	
	ArrayList<Byte> list = new ArrayList<>();
	
	int i = 0;
	
	while ((i = stream.read()) != -1)
	    list.add((byte)i);
	
	bytes = new byte[list.size()];
	
	for (i = 0; i < bytes.length; i++)
	    bytes[i] = list.get(i);


	stream.close();
	return bytes;
    }
    
    public byte [] getFileBytes(String filename) throws IOException
    {
	return this.getInputStreamBytes(new FileInputStream(filename));
    }

    public byte [] getURLBytes(String url) throws IOException
    {
	URL uri = new URL(url);
	return  getInputStreamBytes(uri.openStream());
    }
}
