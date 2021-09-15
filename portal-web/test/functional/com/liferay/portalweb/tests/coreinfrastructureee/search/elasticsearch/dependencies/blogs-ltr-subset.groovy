import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil
import com.liferay.portal.kernel.util.Validator;
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.time.LocalDateTime
import java.time.Duration


def getServiceContext() {
 def groups = GroupLocalServiceUtil.getGroups(-1, -1);
 def group = null;
 for (g in groups) {
  if (g.getName(LocaleUtil.US) == "Guest") {
   group = g;
  }
 }
 def sc = ServiceContextThreadLocal.getServiceContext();
 sc.scopeGroupId = group.groupId;
 return sc;
}

def importMovieAsBlogEntry(movie) {
 def sc = getServiceContext();
 def blogEntry = BlogsEntryLocalServiceUtil.addEntry(sc.userId, movie.title, movie.content, new Date(), sc);
 if (Validator.isNotNull(movie.assetTagName)) {
  System.err.println("Tagging " + movie.title + " with " + movie.assetTagName);
  entry = BlogsEntryLocalServiceUtil.updateAsset(sc.userId, blogEntry, new long[0], (String[])[movie.assetTagName.replaceAll($/[\[\]&'@"}:,=>/<;*/~+#`?]/$, "")], new long[0], 1.0);
 }
}

def startDateTime = LocalDateTime.now();
System.err.println("start importing @ " + startDateTime)

def data = [
  [
    "title":"""Alien Nation""", "content":"""A few years from now, Earth will have the first contact with an alien civilisation. These aliens, known as Newcomers, slowly begin to be integrated into human society after years of quarantine.""","assetTagName":"""alien nation movie collection"""
  ],
  [
    "title":"""Alien³""", "content":"""After escaping with Newt and Hicks from the alien planet, Ripley crash lands on Fiorina 161, a prison planet and host to a correctional facility. Unfortunately, although Newt and Hicks do not survive the crash, a more unwelcome visitor does. The prison does not allow weapons of any kind, and with aid being a long time away, the prisoners must simply survive in any way they can.""","assetTagName":"""alien collection"""
  ],
  [
    "title":"""Alien""", "content":"""During its return to the earth, commercial spaceship Nostromo intercepts a distress signal from a distant planet. When a three-member team of the crew discovers a chamber containing thousands of eggs on the planet, a creature inside one of the eggs attacks an explorer. The entire crew is unaware of the impending nightmare set to descend upon them when the alien parasite planted inside its unfortunate host is birthed.""","assetTagName":"""alien collection"""
  ],
  [
    "title":"""Alien Outpost""", "content":"""A documentary crew follows an elite unit of soldiers in the wake of an alien invasion.""","assetTagName":""""""
  ],
  [
    "title":"""Alien Abduction""", "content":"""A vacationing family encounters an alien threat in this pulse-pounding thriller based on the real-life Brown Mountain Lights phenomenon in North Carolina.""","assetTagName":""""""
  ],
  [
    "title":"""Aliens""", "content":"""When Ripley's lifepod is found by a salvage crew over 50 years later, she finds that terra-formers are on the very planet they found the alien species. When the company sends a family of colonists out to investigate her story, all contact is lost with the planet and colonists. They enlist Ripley and the colonial marines to return and search for answers.""","assetTagName":"""alien collection"""
  ],
  [
    "title":"""Alien Space Avenger""", "content":"""In 1939 a spaceship carrying four alien escaped prisoners crash-lands on Earth and the aliens take over the bodies of four locals. Fifty years later the aliens find out that an artist has written a comic book called "Space Avenger," which they believe is about them. They go to New York to try to kill the artist.""","assetTagName":""""""
  ],
  [
    "title":"""Alien: Resurrection""", "content":"""Two hundred years after Lt. Ripley died, a group of scientists clone her, hoping to breed the ultimate weapon. But the new Ripley is full of surprises … as are the new aliens. Ripley must team with a band of smugglers to keep the creatures from reaching Earth.""","assetTagName":"""alien collection"""
  ],
  [
    "title":"""Alien Trespass""", "content":"""In 1957 a fiery object crashes into a mountaintop in the California desert, bringing the threat of disaster to Earth. Out of the flying saucer escapes a murderous creature, the Ghota, bent on destroying all life forms on the planet. A benevolent alien from the spaceship, Urp, inhabits the body of Ted Lewis, an astronomer and with the help of Tammy, a diner waitress, sets out to save mankind.""","assetTagName":""""""
  ],
  [
    "title":"""Mutant Aliens""", "content":"""Mutant Aliens is a wildly humorous yet poignant story about an astronaut, his daughter, and 5 violent, yet sympathetic, alien creatures who seek revenge against a space industry baron.""","assetTagName":""""""
  ],
  [
    "title":"""Alien Predators""", "content":"""3 young friends are on vacation in Spain, until weird things begin to occur and then a man from NASA tells them what's really going on: Dangerous Alien Organisms from the Apollo 14 Moon Mission, which was tested on animals on SkyLab. But then the organisms fell to earth with SkyLab and they are now starting to infect people by brutally killing them off. And these 3 friends and NASA scientist must get an antidote from a NASA Lab nearby and save themselves and the rest of the world.""","assetTagName":""""""
  ],
  [
    "title":"""Alien Cargo""", "content":"""After eight months of hyper-sleep, when Christopher 'Chris' McNiel (Jason London) and Theta Kaplan (Missy Crider) of a Mars cargo transport ship the Solar System Shipping Vessel No.17 (Triple S-17) awaken, they find out something has gone terribly wrong. They've woken up from hyper-sleep almost ten months past their scheduled time, find the ship's internals badly damaged, off course and almost no fuel. What's more, it's discovered the first shift killed each other. As the plot unfolds, something truly evil is discovered on board - an alien biological life form which can psychologically manipulate humans.""","assetTagName":""""""
  ],
  [
    "title":"""Alien Nation: The Enemy Within""", "content":"""When detectives Sikes and Francisco are presented with the mysterious death of an Eeno, Matt is stupefied to discover that George rudely snubs the case. He, like most newcomers, reviles the outcast Eenos. As the case unfolds, George has to reassess his prejudices, and George's family help save the city from an alien threat originating in an Eeno waste disposal facility.""","assetTagName":"""alien nation movie collection"""
  ],
  [
    "title":"""Aliens vs Predator: Requiem""", "content":"""A sequel to 2004's Alien vs. Predator, the iconic creatures from two of the scariest film franchises in movie history wage their most brutal battle ever - in our own backyard. The small town of Gunnison, Colorado becomes a war zone between two of the deadliest extra-terrestrial life forms - the Alien and the Predator. When a Predator scout ship crash-lands in the hills outside the town, Alien Facehuggers and a hybrid Alien/Predator are released and begin to terrorize the town.""","assetTagName":"""avp collection"""
  ],
  [
    "title":"""Alien Nation: Dark Horizon""", "content":"""Followup movie to the TV series about 250,000 aliens, or "newcomers" as they are known, who have by now settled alongside the humans in California. Most of the newcomers were slaves, and the slave masters are now looking for them. They send Aponso to earth to locate the slaves ready for the aliens to pick them up.""","assetTagName":"""alien nation movie collection"""
  ],
  [
    "title":"""AVP: Alien vs. Predator""", "content":"""When scientists discover something in the Arctic that appears to be a buried Pyramid, they send a research team out to investigate. Little do they know that they are about to step into a hunting ground where Aliens are grown as sport for the Predator race.""","assetTagName":"""avp collection"""
  ],
  [
    "title":"""Alien Nation: Millennium""", "content":"""It's 1999, and as the end of the millenium approaches, people are attempting to find spiritual enlightenment. But a few people want to skip all the work that entails, and a holy Tenktonese relic in the hands of a heretic is giving them a shortcut. But it's not quite as easily controlled as she says.""","assetTagName":"""alien nation movie collection"""
  ],
  [
    "title":"""Evil Aliens""", "content":"""The sensationalist reporter Michelle Fox presents the TV show Weird World, with phony matters about UFOS and aliens. When she hears about Cat, a young woman that claims that have been abducted with her boyfriend and become pregnant by aliens, she convinces her chief to travel with a team to the remote Welsh island of Scalled to interview Cat. She invites the cameraman Ricky Anderson with his sound technician partner; the nerd expert in "ufology" and "ley lines" Gavin Gorman; the actress Candy Vixen and an obscure gay actor to prepare the matter. They get a van and wait for the low tide to reach the island, and when they find evidences that aliens are really landed in the location, the ambitious Michelle decides to film her way to fame and wealth.""","assetTagName":""""""
  ],
  [
    "title":"""Codependent Lesbian Space Alien Seeks Same""", "content":"""The adventures of lesbian space aliens on the planet Earth, and the story of the romance between Jane, a shy greeting card store employee, and Zoinx, the woman Jane does not realize is from outer-space. Meanwhile, two government agents, or 'Men In Black,' are closely tracking Jane and the aliens while harboring their own secrets.""","assetTagName":""""""
  ],
  [
    "title":"""Aliens in the Attic""", "content":"""It's summer vacation, but the Pearson family kids are stuck at a boring lake house with their nerdy parents. That is until feisty, little, green aliens crash-land on the roof, with plans to conquer the house AND Earth! Using only their wits, courage and video game-playing skills, the youngsters must band together to defeat the aliens and save the world - but the toughest part might be keeping the whole thing a secret from their parents! Featuring an all-star cast including Ashley Tisdale, Andy Richter, Kevin Nealon, Tim Meadows and Doris Roberts, Aliens In The Attic is the most fun you can have on this planet!""","assetTagName":""""""
  ],
  [
    "title":"""Under the Skin""", "content":"""Jonathan Glazer's atmospheric, visually arresting abstraction stars Scarlett Johansson as a seductive alien who prowls the streets of Glasgow in search of prey: unsuspecting men who fall under her spell, only to be consumed by a strange liquid pool.""","assetTagName":""""""
  ],
  [
    "title":"""The Substitute""", "content":"""6th Grade gets a new substitute teacher. She wants to train the class for an international competition in Paris. But something isn't right. How is she able read kids' minds? Why is she so mean? And how does she manage to convince everyone's parents she is so great when the whole class knows she is really an alien?""","assetTagName":""""""
  ],
  [
    "title":"""Rust and Bone""", "content":"""Put in charge of his young son, Ali leaves Belgium for Antibes to live with his sister and her husband as a family. Ali's bond with Stephanie, a killer whale trainer, grows deeper after Stephanie suffers a horrible accident.""","assetTagName":""""""
  ],
  [
    "title":"""The Thing""", "content":"""Scientists in the Antarctic are confronted by a shape-shifting alien that assumes the appearance of the people that it kills.""","assetTagName":""""""
  ],
  [
    "title":"""Sanctum""", "content":"""The 3-D action-thriller Sanctum, from executive producer James Cameron, follows a team of underwater cave divers on a treacherous expedition to the largest, most beautiful and least accessible cave system on Earth. When a tropical storm forces them deep into the caverns, they must fight raging water, deadly terrain and creeping panic as they search for an unknown escape route to the sea. Master diver Frank McGuire (Richard Roxburgh) has explored the South Pacific's Esa-ala Caves for months. But when his exit is cut off in a flash flood, Frank's team--including 17-year-old son Josh (Rhys Wakefield) and financier Carl Hurley (Ioan Gruffudd)--are forced to radically alter plans. With dwindling supplies, the crew must navigate an underwater labyrinth to make it out. Soon, they are confronted with the unavoidable question: Can they survive, or will they be trapped forever?""","assetTagName":""""""
  ],
  [
    "title":"""It's All True""", "content":"""A documentary about Orson Welles's unfinished three-part film about South America.""","assetTagName":""""""
  ],
  [
    "title":"""Alaska""", "content":"""Jake Barnes and his two kids, Sean and Jessie, have moved to Alaska after his wife died. He is a former airline pilot now delivering toilet paper across the mountains. During an emergency delivery in a storm his plane goes down somewhere in the mountains. Annoyed that the authorities aren't doing enough, Jessie and Sean set out on an adventure to find their father with the help of a polar bear which they have saved from a ferocious poacher. Conflict ensues.""","assetTagName":""""""
  ],
  [
    "title":"""One Nine Nine Four""", "content":"""One Nine Nine Four is a documentary film written and directed by Jai Al-Attas, "exploring the birth, growth and eventual tipping point of punk rock during the 90s" . The bulk of the film's content consists of band interviews and archive footage.  The film is narrated by skateboarder Tony Hawk and features interviews and footage of various bands and figures in the punk scene including Billie Joe Armstrong of Green Day, Dexter Holland from The Offspring, Greg Graffin and Brett Gurewitz from Bad Religion, Tim Armstrong, Matt Freeman (previously of Operation Ivy) and Lars Fredriksen from Rancid, Fat Mike from NOFX as well as Mark Hoppus and Tom DeLonge from Blink-182 .""","assetTagName":""""""
  ],
  [
    "title":"""Bad Luck Love""", "content":"""A shocking crime forces a man to re-evaluate his life, only to find that going straight is more complicated than he imagined in this acclaimed drama from Finland. Ali (Jorma Tommila) is a low-level criminal who spends much of his spare time working out at a gym with his brother Pulu (Tommi Eronen). Ali spends most of his day stoned on marijuana, while Pulu is known to drink cleaning products when he's run out of booze. Ali has fallen in love with Inka (Maria Jarvenhelmi), a bright woman with a mind of her own, and her independent nature occasionally throws him into a fit of jealousy. Bad Luck Love was the first film to be nominated in all 12 categories at the Jussis Awards, the Finnish Academy Awards.""","assetTagName":""""""
  ],
  [
    "title":"""Alpha and Omega 3: The Great Wolf Games""", "content":"""Join the pack in this wild, warmhearted and totally pawsome adventure starring everyone's favorite alphas and omegas! It's time for "The Great Wolf Games," when all the alphas in the packs set aside their differences for some friendly competition. When an unexpected accident puts many of our pack's star alpha wolves out of commission, a new team is assembled that includes forest friends not in the pack. Can Coach Humphrey lead his ragtag group of "underdogs" to victory? Find out in this thrilling movie that will leave you howling for more!""","assetTagName":"""alpha and omega collection"""
  ],
  [
    "title":"""Home Alone 4""", "content":"""Kevin McCallister's parents have split up. Now living with his mom, he decides to spend Christmas with his dad at the mansion of his father's rich girlfriend, Natalie. Meanwhile robber Marv Merchants, one of the villains from the first two movies, partners up with a new criminal named Vera to hit Natalie's mansion.""","assetTagName":"""home alone collection"""
  ],
  [
    "title":"""The Deep Six""", "content":"""The conflict between duty and conscience is explored in the WWII drama The Deep Six. Alan Ladd stars as Naval gunnery officer Alec Austin, a Quaker whose sincere pacifist sentiments do not sit well with his crew members. When he refuses to fire upon an unidentified plane, the word spreads that Austin cannot be relied upon in battle (never mind that the plane turns out to be one of ours). To prove that he's worthy of command, Austin volunteers for a dangerous mission: the rescue of a group of US pilots on a Japanese-held island. The ubiquitous William Bendix costars as Frenchy Shapiro (!), Austin's Jewish petty officer and severest critic. If the film has a villain, it is Keenan Wynn as ambitious Lt. Commander Edge, who seems to despise anyone who isn't a mainline WASP.""","assetTagName":""""""
  ],
  [
    "title":"""Buried Alive II""", "content":"""A woman inherits a fortune, causing her husband and his lover to plot her demise by poisoning her. The only trouble is it only places her in a deep coma that resembles death. When an accident occurs in the embalmer's office he doesn't complete the embalming process, causing her to be buried alive. Awakening from the grave, she claws her way out and seeks revenge against the two who caused her supposed demise""","assetTagName":""""""
  ],
  [
    "title":"""Ratchet &amp; Clank""", "content":"""Ratchet and Clank tells the story of two unlikely heroes as they struggle to stop a vile alien named Chairman Drek from destroying every planet in the Solana Galaxy. When the two stumble upon a dangerous weapon capable of destroying entire planets, they must join forces with a team of colorful heroes called The Galactic Rangers in order to save the galaxy. Along the way they'll learn about heroism, friendship, and the importance of discovering one's own identity.""","assetTagName":""""""
  ],
  [
    "title":"""The Heat's On""", "content":"""After an absence of three years, Mae West returned to the screen in the musical comedy The Heat's On. La West is cast as Fay Lawrence, a famous Broadway actress who is loved intensely by her producer Tony Ferris (William Gaxton). Rival producer Forrest Stanton (Alan Dinehart) steals Fay away from Ferris by convincing her that she's been blacklisted from Broadway by blue-nosed moralist Hannah Bainbridge (Almira Sessions). Meanwhile, Hannah's puckish brother Hubert (Victor Moore) syphons money from his sister's "clean up show business" committee to produce a musical show for his actress niece Janey (Mary Roche). Somehow, all these characters converge for a spectacular closing production number spotlighting the formidable Fay. Part of the reason for the failure of The Heat's On is the fact that Mae West didn't write her own dialogue, as was usually her custom. The film performed so poorly that it would be 27 years before West would again appear on the Big Screen.""","assetTagName":""""""
  ],
  [
    "title":"""An All Dogs Christmas Carol""", "content":"""Charlie B. Barker and Itchy Ford are back with Sasha and the gang having a Dicken's of a time as they try to save Christmas from Carface and an evil spirit that wishes to use dogs all over the world to ruin Christmas forever.""","assetTagName":"""all dogs go to heaven collection"""
  ],
  [
    "title":"""Alan Partridge: Alpha Papa""", "content":"""Alan Partridge has had many ups and downs in life. National television broadcaster. Responsible for killing a guest on live TV. Local radio broadcaster. Nervous breakdown in Dundee. A self-published book, 'Bouncing Back', which was subsequently remaindered and pulped. Alan Partridge: Alpha Papa portrays the events of the greatest low-to-high ebb spectrum in his life to date, namely how he tries to salvage his public career while negotiating a potentially violent turn of events at North Norfolk Digital Radio.""","assetTagName":""""""
  ],
  [
    "title":"""All the Light in the Sky""", "content":"""An insomniac actress is facing the waning days of her career, when her niece pays a visit to her Malibu house.""","assetTagName":""""""
  ],
  [
    "title":"""All the Little Animals""", "content":"""An emotionally challenged young man named Bobby (Christian Bale) runs away from home in order to escape his abusive stepfather who has killed his pets. He meets an old man, Mr. Summers (John Hurt), who spends his time traveling and giving burials to animals that have been killed by cars. Bobby, also having an affinity for animals, becomes friends with the old man and aids him in his task.""","assetTagName":""""""
  ],
  [
    "title":"""The Hidden""", "content":"""An alien is on the run in America. To get his kicks, it kills anything that gets in its way, and uses the body as a new hiding place. This alien has a goal in life; power. Hotly pursued by another alien (who's borrowed the body of a dead FBI agent), lots of innocent people die in the chase.""","assetTagName":"""hidden collection"""
  ]
]
def count = 0
def total = data.size()
data.each {
 movie ->
  try {
   count = count + 1;
   System.err.println("Importing " + movie.title + " (" + count + " of " + total + ")");
   importMovieAsBlogEntry(movie);
  } catch (Exception e) {
   System.err.println("ERROR: " + e);
   e.printStackTrace()
   System.err.println(count);
  }
}
def endDateTime = LocalDateTime.now()
System.err.println("import done @ " + endDateTime + "(" + Duration.between(endDateTime, startDateTime) + ")")