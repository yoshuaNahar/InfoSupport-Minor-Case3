package nl.kantilever.webwinkel.events.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kantilever.webwinkel.domain.Artikel;
import nl.kantilever.webwinkel.services.ArtikelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RabbitMQ Listener which is triggered when the Maintenance Assignment Status is set to Ready.
 */
@Component
public class ArtikelAanCatalogusToegevoegdListener {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private ArtikelService artikelService;

  @Autowired
  public void setArtikelService(ArtikelService artikelService) {
    this.artikelService = artikelService;
  }


  @RabbitListener(bindings = @QueueBinding(
    value = @Queue(value = "Kantilever.WebwinkelService.Replay", durable = "false"),
    exchange = @Exchange(value = "KantileverBus", ignoreDeclarationExceptions = "true", durable = "false", type = ExchangeTypes.TOPIC),
    key = "Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd")
  )
  public void listen(byte[] test) throws IOException {
    logger.debug("Event Caught: Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd");
    String s = new String(test);

    Artikel ontvangenArtikel = objectMapper.readValue(s, new TypeReference<Artikel>(){});
    if(ontvangenArtikel !=null){
      this.artikelService.save(ontvangenArtikel);
    }
  }

}
