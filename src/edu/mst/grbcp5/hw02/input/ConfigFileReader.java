package edu.mst.grbcp5.hw02.input;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigFileReader {

  private final File xmlFile;
  private final Document document;
  private final Node rootNode;
  private final NodeList parameterNodeList;
  private final int numChildNodes;

  public ConfigFileReader( final String configFilePath ) throws
    ParserConfigurationException, IOException, SAXException {

    /* Local variables */
    DocumentBuilderFactory documentBuilderFactory;
    DocumentBuilder documentBuilder;
    NodeList parameters;
    Node node;
    Element element;

    xmlFile = new File( configFilePath );
    documentBuilderFactory = DocumentBuilderFactory.newInstance();

    /* Throws ParserConfigurationException */
    documentBuilder = documentBuilderFactory.newDocumentBuilder();

    /* Throws SAXException, IOException */
    this.document = documentBuilder.parse( this.xmlFile );

    /* Read document */
    this.document.getDocumentElement().normalize();
    this.rootNode = document.getChildNodes().item( 0 );
    this.parameterNodeList = rootNode.getChildNodes();
    this.numChildNodes = this.parameterNodeList.getLength();


  }

  private String[] convertToArray( NodeList list ) {

    List< String > result = new LinkedList<>();
    String trimmedString;

    for ( int i = 0; i < list.getLength(); i++ ) {
      trimmedString = list.item( i ).getTextContent().trim();
      if ( trimmedString.length() > 0 ) {
        result.add( 0, trimmedString );
      }
    }

    return result.toArray( new String[ result.size() ] );
  }

  public Map< String, Object > getParameters() {
    try {
      return getParameters( new String[ 0 ] );
    } catch ( RequiredTagNotFoundException e ) {
      e.printStackTrace();
      return null;
    }
  }

  public Map< String, Object > getParameters( String required[] )
    throws RequiredTagNotFoundException {
    Map< String, Object > parameterMap
      = new HashMap<>( this.numChildNodes );
    Node currentNode;
    Element element;

    if ( required == null ) {
      required = new String[ 0 ];
    }

    List req = Arrays.asList( required );
    StringBuilder sb;

    /* For each child of the root node */
    for ( int p = 0; p < this.numChildNodes; p++ ) {
      currentNode = this.parameterNodeList.item( p );

      if ( currentNode.getNodeType() == Node.ELEMENT_NODE ) {
        element = ( Element ) ( currentNode );
        String nodeText = element.getTextContent();
        String nodeType = element.getAttribute( "dataType" );
        Object value;

        /* Indicate that the requested tag was found */
        req.remove( element.getTagName() );

        switch ( nodeType ) {
          case "Integer":
            value = Integer.parseInt( nodeText );
            break;
          case "Long":
            value = Long.parseLong( nodeText );
            break;
          case "Double":
            value = Double.parseDouble( nodeText );
            break;
          case "Boolean":
            value = Boolean.parseBoolean( nodeText );
            break;
          case "List":
            value = this.convertToArray( element.getChildNodes() );
            break;
          default:
            // Assume string
            value = nodeText;
            break;
        }

        parameterMap.put( element.getTagName(), value );

      }

    }

    /* If we did not find all the required tags */
    if ( !req.isEmpty() ) {
      sb = new StringBuilder();

      sb.append( "Required tags not found: " );
      sb.append( Arrays.toString( req.toArray() ) );

      throw new RequiredTagNotFoundException( sb.toString() );
    }

    return parameterMap;
  }

}
