package utils
{
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;

	public class ServiceUtil
	{
		public static var serviceAddress:String = "";
		
		public static function requestData(functionName:String, params:Object, handler:Function):void
		{
			var request:URLRequest = new URLRequest(serviceAddress + functionName);
			request.method = URLRequestMethod.GET;
			request.data = params;
			var query:URLLoader = new URLLoader(request);
			query.addEventListener(Event.COMPLETE, handler);
		}
	}
}