package todolist.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class BaseService {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	public static String SUCCESS = "SUCCESS";
	public static String FAILE = "FAILE";
}