package nl.kantilever.webwinkel.events.listeners;

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

/**
 * RabbitMQ Listener which is triggered when the Maintenance Assignment Status is set to Ready.
 */
@Component
public class ArtikelAanCatalogusToegevoegdListener {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ArtikelService artikelService;

  @Autowired
  public void setArtikelService(ArtikelService artikelService) {
    this.artikelService = artikelService;
  }

  @RabbitListener(bindings = @QueueBinding(
    value = @Queue,
    exchange = @Exchange(
      value = "KantileverBus",
      type = ExchangeTypes.TOPIC),
    key = "Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd"))
  public void listen(Artikel artikel) {
    logger.debug("Event Caught: Kantilever.CatalogusService.ArtikelAanCatalogusToegevoegd");
    this.artikelService.save(artikel);
  }
}
