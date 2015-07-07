package gov.pr.celepar.abi.servlet;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


/**
 * Servlet para exibir uma foto de parametroEmail gravada no banco de dados.
 * Uso: <img src="exibirFoto.jpg?cod=99><br>
 * @author felipevillarinho, radoski
 *
 */
public class ExibirFoto extends HttpServlet{

	private static final long serialVersionUID = 1455775436852188475L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
	throws ServletException, IOException {
		final String codigo = request.getParameter("codInstituicao");
		if("imgSessao".equals(codigo)){
			this.mostrarImagemSessao(request, response);
		}else{
			this.mostrarImagemServidor(request, response);
		}
	}

	private void exibirImagem(final HttpServletResponse response, final byte[] sImage, String tipo) throws ApplicationException{
		final  InputStream stream = new ByteArrayInputStream(sImage);
		try {
			
			if (tipo.equals(".JPG")){
			
			response.setContentType("image/jpeg"); 
			response.addHeader("Content-Disposition","filename=exibirFoto.jpg");
	
	
			final  JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(stream);
			
			BufferedImage bImage;
		
			bImage = decoder.decodeAsBufferedImage();
			final  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());

			encoder.encode(bImage);
			
			}
			else{
				response.setContentType("image/png"); 
				response.addHeader("Content-Disposition","filename=exibirFoto.png");
				
				ServletOutputStream ouputStream;

				ouputStream = response.getOutputStream();

				ouputStream.write(sImage, 0, sImage.length);  
			}
			response.flushBuffer();
		
		} catch (ImageFormatException e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"exibirImagem"}, e, ApplicationException.ICON_ERRO);
		} catch (IOException e1) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{"exibirImagem"}, e1, ApplicationException.ICON_ERRO);
		}  finally{
			try {
				if(stream != null)
					stream.close();
			}catch (IOException e2) {
				throw new ApplicationException("mensagem.erro.9001", new String[]{"ExibirFoto.exibirImagem"}, e2, ApplicationException.ICON_ERRO);
			}
		}
	}

	private void mostrarImagemSessao(final HttpServletRequest request, final HttpServletResponse response) throws IOException{
		try{
			final byte[] sImage = (byte[])request.getSession().getAttribute("imagemSessao");
			final String tipo = (String)request.getSession().getAttribute("tipoImagem");
			this.exibirImagem(response, sImage, tipo);
		}catch (Exception e) {
			final BufferedImage bufferedImage = new BufferedImage(140, 30, BufferedImage.TYPE_INT_RGB);
			final Graphics grafico = bufferedImage.getGraphics();
			grafico.setColor(Color.WHITE);
			grafico.fillRect(0, 0, 140, 30);
			grafico.setColor(Color.BLACK);
			grafico.drawString("Imagem indisponível", 10, 20);
			response.setContentType("image/jpeg"); 
			response.addHeader("Content-Disposition","filename=exibirFoto.jpg");
			final  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
			encoder.encode(bufferedImage);
			response.flushBuffer();
		}
	}

	private void mostrarImagemServidor(final HttpServletRequest request, final HttpServletResponse response) throws IOException{
		try{
			final Integer codigo = Integer.valueOf(request.getParameter("codInstituicao"));
			Instituicao i = CadastroFacade.obterInstituicao(codigo);
			final String tipo = i.getLogoInstituicao();
			byte [] bytes = CadastroFacade.obterLogoInstituicao(codigo+tipo);
			request.getSession().setAttribute("imagemSessao", bytes);
			request.getSession().setAttribute("tipoImagem", tipo);
			this.exibirImagem(response, bytes, tipo);
		}catch (Exception e) {
			final BufferedImage bufferedImage = new BufferedImage(140, 30, BufferedImage.TYPE_INT_RGB);
			final Graphics grafico = bufferedImage.getGraphics();
			grafico.setColor(Color.WHITE);
			grafico.fillRect(0, 0, 140, 30);
			grafico.setColor(Color.BLACK);
			grafico.drawString("Imagem indisponível", 10, 20);
			response.setContentType("image/jpeg"); 
			response.addHeader("Content-Disposition","filename=exibirFoto.jpg");
			final  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
			encoder.encode(bufferedImage);
			response.flushBuffer();
		}
	}

	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
