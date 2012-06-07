package de.lmu.ifi.dbs.elki.math.statistics.distribution;

/*
 This file is part of ELKI:
 Environment for Developing KDD-Applications Supported by Index-Structures

 Copyright (C) 2012
 Ludwig-Maximilians-Universität München
 Lehr- und Forschungseinheit für Datenbanksysteme
 ELKI Development Team

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import static org.junit.Assert.*;

import org.junit.Test;

import de.lmu.ifi.dbs.elki.JUnit4Test;

/**
 * Unit test for the Chi Squared distribution in ELKI.
 * 
 * The reference values were computed using GNU R and SciPy.
 * 
 * @author Erich Schubert
 */
public class TestChiSquaredDistribution implements JUnit4Test {
  public static final double[] P_PROBIT = { //
  0.0001, 0.001, 0.01, 0.1, 0.25, 0.5, 0.75, 0.9, 0.99, 0.999, 0.9999 //
  };

  public static final double[] SCIPY_CHISQ_PROBIT_01 = { //
  1.16892641156733644781219710813445996321826192936318e-80, // 0.000100
  1.16892641146821240352815603348024825656653422803765e-60, // 0.001000
  1.16892641145814869325987350586058419394529211182657e-40, // 0.010000
  1.16892641145721739436487227654675467673153240504758e-20, // 0.100000
  1.06313237798343417157777858600350238808297798076552e-12, // 0.250000
  1.11477568814925462586246096563513674482237547636032e-06, // 0.500000
  3.71347136769252343657665704768078285269439220428467e-03, // 0.750000
  1.52634227818377987695441788673633709549903869628906e-01, // 0.900000
  2.17525480018361871970000720466487109661102294921875e+00, // 0.990000
  5.47291719745734983604279477731324732303619384765625e+00, // 0.999000
  9.24820164420099821711573895299807190895080566406250e+00, // 0.999900
  };

  public static final double[] GNUR_CHISQ_PROBIT_01 = { //
  1.16892641145733484384707827779994069422035569294114e-80, // 0.000100
  1.16892641145731079523768921414073055688308402765104e-60, // 0.001000
  1.16892641145731997959243769324569198659341653028700e-40, // 0.010000
  1.16892641145730436213892351266574423573550225567180e-20, // 0.100000
  1.06313237798339660917691558230537590321561347561641e-12, // 0.250000
  1.11477568814925102597243513485869570445174758788198e-06, // 0.500000
  3.71347136769249915044799337238146108575165271759033e-03, // 0.750000
  1.52634227818377238294900166692968923598527908325195e-01, // 0.900000
  2.17525480018361827561079735460225492715835571289062e+00, // 0.990000
  5.47291719745735161239963417756371200084686279296875e+00, // 0.999000
  9.24820164420099821711573895299807190895080566406250e+00, // 0.999900
  };

  public static final double[] SCIPY_CHISQ_PROBIT_1 = { //
  1.57079633500628175845386844643181145642074625357054e-08, // 0.000100
  1.57079714926323284399762268692679612058782367967069e-06, // 0.001000
  1.57087857909697853949632095904576090106274932622910e-04, // 0.010000
  1.57907740934312285085994176370149943977594375610352e-02, // 0.100000
  1.01531044267621481380636794256133725866675376892090e-01, // 0.250000
  4.54936423119572663775755927417776547372341156005859e-01, // 0.500000
  1.32330369693146554510576606844551861286163330078125e+00, // 0.750000
  2.70554345409541641132022959936875849962234497070312e+00, // 0.900000
  6.63489660102121447948775312397629022598266601562500e+00, // 0.990000
  1.08275661706627310820749698905274271965026855468750e+01, // 0.999000
  1.51367052266236044033576035872101783752441406250000e+01, // 0.999900
  };

  public static final double[] GNUR_CHISQ_PROBIT_1 = { //
  1.57079633501956727170820513452345612570582034095423e-08, // 0.000100
  1.57079714926249020786111747910451796883535280358046e-06, // 0.001000
  1.57087857909702001022941852959036168613238260149956e-04, // 0.010000
  1.57907740934312285085994176370149943977594375610352e-02, // 0.100000
  1.01531044267621550769575833328417502343654632568359e-01, // 0.250000
  4.54936423119572830309209621191257610917091369628906e-01, // 0.500000
  1.32330369693146598919497591850813478231430053710938e+00, // 0.750000
  2.70554345409541507905260004918090999126434326171875e+00, // 0.900000
  6.63489660102121181495249402360059320926666259765625e+00, // 0.990000
  1.08275661706627310820749698905274271965026855468750e+01, // 0.999000
  1.51367052266236044033576035872101783752441406250000e+01, // 0.999900
  };

  public static final double[] SCIPY_CHISQ_PROBIT_2 = { //
  2.00010000666460950081992908877737136208452284336090e-04, // 0.000100
  2.00100066716752279100122180466314603108912706375122e-03, // 0.001000
  2.01006717070028942395687465705123031511902809143066e-02, // 0.010000
  2.10721031315652534976479159922746475785970687866211e-01, // 0.100000
  5.75364144903561913757528145652031525969505310058594e-01, // 0.250000
  1.38629436111989057245352796599036082625389099121094e+00, // 0.500000
  2.77258872223978158899626578204333782196044921875000e+00, // 0.750000
  4.60517018598809180218722758581861853599548339843750e+00, // 0.900000
  9.21034037197618005166077637113630771636962890625000e+00, // 0.990000
  1.38155105579642718538480039569549262523651123046875e+01, // 0.999000
  1.84206807439525839242833171738311648368835449218750e+01, // 0.999900
  };

  public static final double[] GNUR_CHISQ_PROBIT_2 = { //
  2.00010000666716686269427927236108644137857481837273e-04, // 0.000100
  2.00100066716706699240790889859908929793164134025574e-03, // 0.001000
  2.01006717070028838312278907096697366796433925628662e-02, // 0.010000
  2.10721031315652618243206006809487007558345794677734e-01, // 0.100000
  5.75364144903561802735225683136377483606338500976562e-01, // 0.250000
  1.38629436111989057245352796599036082625389099121094e+00, // 0.500000
  2.77258872223978114490705593198072165250778198242188e+00, // 0.750000
  4.60517018598809180218722758581861853599548339843750e+00, // 0.900000
  9.21034037197618005166077637113630771636962890625000e+00, // 0.990000
  1.38155105579642718538480039569549262523651123046875e+01, // 0.999000
  1.84206807439525839242833171738311648368835449218750e+01, // 0.999900
  };

  public static final double[] SCIPY_CHISQ_PROBIT_4 = { //
  2.84184752435540088910670419863890856504440307617188e-02, // 0.000100
  9.08040355389811981723369171959348022937774658203125e-02, // 0.001000
  2.97109480506531686838656014515436254441738128662109e-01, // 0.010000
  1.06362321677922411211625330906827002763748168945312e+00, // 0.100000
  1.92255752622955422559414273564470931887626647949219e+00, // 0.250000
  3.35669398003332153379574265272822231054306030273438e+00, // 0.500000
  5.38526905777939113306729268515482544898986816406250e+00, // 0.750000
  7.77944033973485904454037154209800064563751220703125e+00, // 0.900000
  1.32767041359876234452030985266901552677154541015625e+01, // 0.990000
  1.84668269529031690012743638362735509872436523437500e+01, // 0.999000
  2.35127424449910797932261630194261670112609863281250e+01, // 0.999900
  };

  public static final double[] GNUR_CHISQ_PROBIT_4 = { //
  2.84184752435549976834483487664329004473984241485596e-02, // 0.000100
  9.08040355389791442597413606563350185751914978027344e-02, // 0.001000
  2.97109480506531964394412170804571360349655151367188e-01, // 0.010000
  1.06362321677922389007164838403696194291114807128906e+00, // 0.100000
  1.92255752622955400354953781061340123414993286132812e+00, // 0.250000
  3.35669398003332108970653280266560614109039306640625e+00, // 0.500000
  5.38526905777939202124571238528005778789520263671875e+00, // 0.750000
  7.77944033973485904454037154209800064563751220703125e+00, // 0.900000
  1.32767041359876216688462591264396905899047851562500e+01, // 0.990000
  1.84668269529031690012743638362735509872436523437500e+01, // 0.999000
  2.35127424449910762405124842189252376556396484375000e+01, // 0.999900
  };

  public static final double[] SCIPY_CHISQ_PROBIT_10 = { //
  8.88920357912773129172023800492752343416213989257812e-01, // 0.000100
  1.47874346383567578655515717400703579187393188476562e+00, // 0.001000
  2.55821216018720765106309045222587883472442626953125e+00, // 0.010000
  4.86518205192532882108480407623574137687683105468750e+00, // 0.100000
  6.73720077195464384089973464142531156539916992187500e+00, // 0.250000
  9.34181776559196919151872862130403518676757812500000e+00, // 0.500000
  1.25488613968893769623491607489995658397674560546875e+01, // 0.750000
  1.59871791721052627366361775784753262996673583984375e+01, // 0.900000
  2.32092511589543590844186837784945964813232421875000e+01, // 0.990000
  2.95882984450744146442957571707665920257568359375000e+01, // 0.999000
  3.55640139419523890751406725030392408370971679687500e+01, // 0.999900
  };

  public static final double[] GNUR_CHISQ_PROBIT_10 = { //
  8.88920357912895919838547342806123197078704833984375e-01, // 0.000100
  1.47874346383566490636951584747293964028358459472656e+00, // 0.001000
  2.55821216018720631879546090203803032636642456054688e+00, // 0.010000
  4.86518205192532882108480407623574137687683105468750e+00, // 0.100000
  6.73720077195464206454289524117484688758850097656250e+00, // 0.250000
  9.34181776559196741516188922105357050895690917968750e+00, // 0.500000
  1.25488613968893769623491607489995658397674560546875e+01, // 0.750000
  1.59871791721052591839224987779743969440460205078125e+01, // 0.900000
  2.32092511589543590844186837784945964813232421875000e+01, // 0.990000
  2.95882984450744181970094359712675213813781738281250e+01, // 0.999000
  3.55640139419523890751406725030392408370971679687500e+01, // 0.999900
  };

  @Test
  public void testProbit() {
    checkProbit(1., P_PROBIT, SCIPY_CHISQ_PROBIT_1, 1e-13);
    checkProbit(2., P_PROBIT, SCIPY_CHISQ_PROBIT_2, 1e-13);
    checkProbit(4., P_PROBIT, SCIPY_CHISQ_PROBIT_4, 1e-13);
    checkProbit(10, P_PROBIT, SCIPY_CHISQ_PROBIT_10, 1e-12);
    checkProbit(.1, P_PROBIT, SCIPY_CHISQ_PROBIT_01, 1e-13);
    checkProbit(1., P_PROBIT, GNUR_CHISQ_PROBIT_1, 1e-13);
    checkProbit(2., P_PROBIT, GNUR_CHISQ_PROBIT_2, 1e-13);
    checkProbit(4., P_PROBIT, GNUR_CHISQ_PROBIT_4, 1e-13);
    checkProbit(10, P_PROBIT, GNUR_CHISQ_PROBIT_10, 1e-13);
    checkProbit(.1, P_PROBIT, GNUR_CHISQ_PROBIT_01, 1e-13);
  }

  private void checkProbit(double nu, double[] x, double[] expected, double err) {
    ChiSquaredDistribution d = new ChiSquaredDistribution(nu);
    for(int i = 0; i < x.length; i++) {
      double val = d.probit(x[i]);
      if(val == expected[i]) {
        continue;
      }
      double diff = Math.abs(val - expected[i]);
      if(diff < err || diff / expected[i] < err) {
        continue;
      }
      final int e1 = (int) Math.ceil(Math.log(diff / expected[i]) / Math.log(10));
      final int e2 = (int) Math.ceil(Math.log(diff) / Math.log(10));
      final int errlev = Math.max(e1, e2);
      // System.err.println(nu+" "+errlev+" "+val+" "+expected[i]+" "+diff);
      assertEquals("Error magnitude: 1e" + errlev, expected[i], val, err);
    }
  }
}