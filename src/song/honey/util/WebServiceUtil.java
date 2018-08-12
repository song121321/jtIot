package song.honey.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebServiceUtil {

	public static ArrayList<String> getArrayOfAnyType(String nameSpace,
			String methodName, String endPoint, HashMap<String, String> params) {

		ArrayList<String> result = null;
		String soapAction = nameSpace + methodName;// instantiate soapAction
		HttpTransportSE transport = new HttpTransportSE(endPoint);// create a
																	// instance
																	// of http
																	// transport
		transport.debug = true;

		SoapObject outSoapObject = new SoapObject(nameSpace, methodName);// instantiate
																			// a
																			// soap
																			// object
																			// that
																			// will
																			// be
																			// sent
		for (Map.Entry<String, String> entry : params.entrySet()) {// put
																	// parameters
																	// into that
																	// soapObject
			outSoapObject.addProperty(entry.getKey(), entry.getValue());
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(outSoapObject);// assigns that
													// soapObject(as outbound
													// message) to the
													// envelope.把这个发往外地的对象放入信封，以供"soap调用"

		try {
			transport.call(soapAction, envelope);// remote call
			if (envelope.getResponse() != null) {
				SoapObject inSoapObject = (SoapObject) envelope.bodyIn;// .getResponse();//get
																		// the
																		// response
				SoapObject soapObjectDetail = (SoapObject) inSoapObject
						.getProperty(0);
				int propertyCount = soapObjectDetail.getPropertyCount();
				result = new ArrayList<String>();
				for (int i = 0; i < propertyCount; i++) {
					result.add(soapObjectDetail.getProperty(i).toString());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;// must judge null
	}

	public static String getAnyType(String nameSpace, String methodName,
			String endPoint, HashMap<String, String> params) {
		String result = "";
		String soapAction = nameSpace + methodName;// instantiate soapAction
		HttpTransportSE transport = new HttpTransportSE(endPoint);
		transport.debug = true;

		SoapObject outSoapObject = new SoapObject(nameSpace, methodName);

		for (Map.Entry<String, String> entry : params.entrySet()) {

			outSoapObject.addProperty(entry.getKey(), entry.getValue());
		}

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(outSoapObject);
		try {
			transport.call(soapAction, envelope);
			if (envelope.getResponse() != null) {
				SoapObject object = (SoapObject) envelope.bodyIn;
				result = object.getProperty(0).toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
