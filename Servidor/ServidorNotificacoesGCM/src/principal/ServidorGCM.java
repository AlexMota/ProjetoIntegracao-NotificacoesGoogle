package principal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class ServidorGCM {

	final String URL = "https://android.googleapis.com/gcm/send";
	final String apiKey = "AIzaSyD4-Woy8xTAI-eQyWBS6R-jzQEKFYW7uE0";
	private TreeSet<String> listaRegId;

	
	
	

	public ServidorGCM() {
		listaRegId = new TreeSet<String>();
		//listaRegId.add("");//Adcione na mão um registrationId para teste se for necessario
		
	}





	public void enviarMensagem(String registrationID, String mensagem) {
		CloseableHttpClient client = HttpClients.createDefault();

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		HttpPost httpPost = new HttpPost(URL);

		list.add(new BasicNameValuePair("registration_id", registrationID));
		list.add(new BasicNameValuePair("data.mensagem", mensagem));

		httpPost.setHeader("Authorization", "key=" + apiKey);
		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			client.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void adicionarRegId(String regId){
		
		listaRegId.add(regId);
		
	}
	
	public void enviarMsgTodosRegId(String mensagem){
		
		Iterator<String> iterator = listaRegId.iterator();
		String regAtual;
		
		while (iterator.hasNext()) {
			regAtual = iterator.next();
			enviarMensagem(regAtual, mensagem);
		}
		
	}
	
	public TreeSet<String> getListaregId(){
		return listaRegId;
	}

}
