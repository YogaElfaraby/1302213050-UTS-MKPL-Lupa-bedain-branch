package lib;

public class TaxFunction {


		/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	
	private static final double TAX_RATE = 0.05;
    private static final double BASE_TAX_FREE_INCOME = 54000000;
    private static final double ADDITIONAL_TAX_FREE_INCOME_PER_CHILD = 4500000;
    private static final int MAX_CHILDREN_FOR_TAX_FREE_INCOME = 3;

	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthsWorked, int deductible, boolean isMarried, int numberOfChildren) {
        validateMonthsWorked(numberOfMonthsWorked);
        
        double totalIncome = calculateTotalIncome(monthlySalary, otherMonthlyIncome, numberOfMonthsWorked);
        double totalTaxFreeIncome = calculateTotalTaxFreeIncome(isMarried, numberOfChildren);
        double taxableIncome = totalIncome - deductible - totalTaxFreeIncome;

        int tax = (int) Math.round(TAX_RATE * taxableIncome);
        return Math.max(tax, 0);
    }
	
	private static void validateMonthsWorked(int numberOfMonthsWorked) {
        if (numberOfMonthsWorked > 12) {
            throw new IllegalArgumentException("Number of months worked cannot exceed 12 per year.");
        }
    }

	private static double calculateTotalIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthsWorked) {
        return (monthlySalary + otherMonthlyIncome) * numberOfMonthsWorked;
    }

	private static double calculateTotalTaxFreeIncome(boolean isMarried, int numberOfChildren) {
        double totalTaxFreeIncome = BASE_TAX_FREE_INCOME;

        if (isMarried) {
            totalTaxFreeIncome += numberOfChildren * ADDITIONAL_TAX_FREE_INCOME_PER_CHILD;
            totalTaxFreeIncome += Math.min(numberOfChildren, MAX_CHILDREN_FOR_TAX_FREE_INCOME - 1) * ADDITIONAL_TAX_FREE_INCOME_PER_CHILD;
        }

        return totalTaxFreeIncome;
    }

	
}
