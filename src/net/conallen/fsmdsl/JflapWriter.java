package net.conallen.fsmdsl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class JflapWriter {

	public void writeFile(File outfile, List<Transition> transitions) throws IOException {
		Set<State> states = new HashSet<State>();
		for (Transition transition : transitions) {
			states.add(transition.getStart());
			states.add(transition.getEnd());
		}

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element structureElement = doc.createElement("structure");
			doc.appendChild(structureElement);

			Element type = doc.createElement("type");
			type.setTextContent("fa");
			structureElement.appendChild(type);

			Element automation = doc.createElement("automaton");
			structureElement.appendChild(automation);

			// states
			for (State state : states) {

				Element stateElm = doc.createElement("state");
				stateElm.setAttribute("id", Integer.toString(state.getId()));
				stateElm.setAttribute("name", state.getName());
				if (state.isFinal()) {
					Element finalElm = doc.createElement("final");
					stateElm.appendChild(finalElm);
				}
				if (state.isInitial()) {
					Element intialElm = doc.createElement("initial");
					stateElm.appendChild(intialElm);
				}

				automation.appendChild(stateElm);
			}

			// transitions
			for (Transition transition : transitions) {
				Element transitionElm = doc.createElement("transition");
				Element fromElm = doc.createElement("from");
				String id = Integer.toString(transition.getStart().getId());
				fromElm.setTextContent(id);
				transitionElm.appendChild(fromElm);

				Element toElm = doc.createElement("to");
				id = Integer.toString(transition.getEnd().getId());
				toElm.setTextContent(id);
				transitionElm.appendChild(toElm);

				Element readElm = doc.createElement("read");
				String input = transition.getInput();
				readElm.setTextContent(input);
				transitionElm.appendChild(readElm);

				automation.appendChild(transitionElm);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(outfile);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

}
